package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FriendList {

    private static ObservableList<Friend> Contacts = FXCollections.observableArrayList();

    public static void init() {
        Contacts = FXCollections.observableArrayList();
    }

    public static void addFriendObject(Friend friend) {
        Contacts.add(friend);
    }

    public static void removeAFriendObject(Friend friend) {
        Contacts.remove(friend);
    }

    public static void removeAllFriendObjects() {
        Contacts.clear();
    }

    public static ObservableList<Friend> getContacts()
    {
        return Contacts;
    }
}
 