package application;

import java.io.*;
import java.util.*;

public class Json {
    private String data = "";
    private String key;
    private String value;
    private Map<String,String> parse;

    public Json() {
        data = "";
    }

    public void setjson(String s) {
        this.data = s;
    }

    public Map<String,String> getjson() {
        Map<String,String> temp = new HashMap<>();
        int length = data.length();
        for (int i=0; i<length; i++) {
            if (data.charAt(i) == '"') {
                key = "";
                i += 1;
                for (i=i; i<length; i++) {
                    if (data.charAt(i) == '"') break;
                    key += data.charAt(i);
                }
                for (i=i; i<length; i++) {
                    if (data.charAt(i) == ':') {
                        i += 1;
                        break;
                    }
                }
                value = "";
                boolean checkfirstspace = true;
                for (i=i; i<length; i++) {
                    if (data.charAt(i) == '"') {
                        for (i=i+1; i<length; i++) {
                            if (data.charAt(i) == '"') {
                                break;
                            }
                            value += data.charAt(i);
                        }
                        break;
                    }
                    if (data.charAt(i) == ',') {
                        break;
                    }
                    if (data.charAt(i) == '}') {
                        break;
                    }
                    if ((checkfirstspace) && (data.charAt(i) == ' ')) continue;
                    value += data.charAt(i);
                    checkfirstspace = false;
                }
                temp.put(key,value);
            }
        }
        parse = temp;
        return temp;
    }

    public void listJson(Map<String,String> parse) {
        for(Map.Entry<String,String> entry : parse.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
