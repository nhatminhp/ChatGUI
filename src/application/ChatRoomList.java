package application;

import java.util.ArrayList;

public class ChatRoomList {

    private static ArrayList<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();

    public static void addAChatRoomObject(ChatRoom chatRoom) {
        chatRoomList.add(chatRoom);
    }

    public static void removeAChatRoomObject(ChatRoom chatRoom) {
        chatRoomList.remove(chatRoom);
    }

    public static void removeAllChatRoomObjects() {
        chatRoomList.clear();
    }

    public ChatRoom searchRoom(String roomName) {
        for (ChatRoom chatRoom:
                chatRoomList) {
            if (chatRoom.getRoomName().equals(roomName)) {
                return chatRoom;
            }
        }
        return null;
    }

}
