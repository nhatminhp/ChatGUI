package application;

import java.util.ArrayList;

public class FriendList {

    public static ArrayList<Friend> Contacts;

    public static void init() {
        Contacts = new ArrayList<Friend>();
    }

    public static void addFriendObject(Friend friend) {
        Contacts.add(friend);
    }

    public static void removeAFriendObject(Friend friend) {
        Contacts.remove(friend);
    }

    public static void removeAllFriendObject() {
        Contacts.clear();
    }
}
 