package application;

import java.util.ArrayList;

public class ChatRoom {

    private int roomID;

    private String lastMessage;

    private String sendingTime;

    private int unreadMessages;

    private ArrayList<User> UserList = new ArrayList<User>();

    public ChatRoom(int roomID) {
        this.setRoomID(roomID);
    }

    public void addUserObject(User user) {
        UserList.add(user);
    }

    public void removeAnUserObject(User user) {
        UserList.remove(user);
    }

    public void removeAllUserObjects() {
        UserList.clear();
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
}
