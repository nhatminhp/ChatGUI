package message;

import java.io.Serializable;

public class Message implements Serializable {

    private String username;
    private String msg;

    public Message(String username, String msg) {
        setUsername(username);
        setMsg(msg);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
