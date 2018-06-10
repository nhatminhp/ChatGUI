package message;

import java.io.Serializable;

public class Message implements Serializable {

    private int roomID;
    private int fromID;
    private String msg;
    private String sendingTime;

    public Message() {
        msg = "";
        sendingTime = "";
    }

    public Message(int _roomID, int _fromID, String _msg, String _sendingTime) {
        setRoomID(_roomID);
        setFromID(_fromID);
        setMsg(_msg);
        setSendingTime(_sendingTime);
    }


    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String toString()
    {
        String t = Integer.toString(getRoomID()) + "; " + Integer.toString(getFromID()) + "; " + getMsg() + "; " + getSendingTime();
        return t;
    }
}
