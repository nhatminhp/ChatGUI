package application;

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.charset.*;

public class Connect {

    private URL url;

    private Map<String,String> arguments;

    public Connect() throws MalformedURLException {
        this.url = new URL("http://localhost:8080/LoginServlet");
        this.arguments = new HashMap<>();
    }

    public void setURL(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public void addArgument(String key,String value) {
        arguments.put(key,value);
    }

    public byte[] joinArgument() {
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : this.arguments.entrySet()) {
            try {
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        return out;
    }

    public String connect() throws IOException {
        byte[] out = joinArgument();
        int length = out.length;
        HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);
        conn.setFixedLengthStreamingMode(length);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.connect();
        try(OutputStream os = conn.getOutputStream()) {
            os.write(out);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        String inputLine;
        String output = "";
        while ((inputLine = in.readLine()) != null)
            //System.out.println(inputLine);
            output = output + inputLine;
        in.close();
        return output;
    }

//    public static void main(String[] args) throws Exception {


//        Map<String,String> arguments = new HashMap<>();
//        arguments.put("user", "hungphan");
//        arguments.put("pwd", "hungphan"); // This is a fake password obviously
//        StringJoiner sj = new StringJoiner("&");
//        for(Map.Entry<String,String> entry : arguments.entrySet())
//            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
//                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
//        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
//        int length = out.length;
//        URL yahoo = new URL("http://localhost:8080/LoginServlet");
//        HttpURLConnection conn = (HttpURLConnection) yahoo.openConnection();
//        conn.setDoOutput(true);
//        conn.setFixedLengthStreamingMode(length);
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        conn.connect();
//        try(OutputStream os = conn.getOutputStream()) {
//            os.write(out);
//        }
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(
//                        conn.getInputStream()));
//        String inputLine;
//
//        while ((inputLine = in.readLine()) != null)
//            System.out.println(inputLine);
//        in.close();
//    }
}