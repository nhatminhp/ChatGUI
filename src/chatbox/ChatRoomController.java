package chatbox;

import application.ChatRoom;
import application.Connect;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoomController {

    @FXML private ListView<ChatRoom> ChatRoomListView;

    private ChatController chatController;

    public void bindChatRoomListView(ObservableList<ChatRoom> list)
    {
        ChatRoomListView.setItems(list);
    }

    public void initialize() {
        ChatRoomListView.setCellFactory(new Callback<ListView<ChatRoom>, ListCell<ChatRoom>>(){

            @Override
            public ListCell<ChatRoom> call(ListView<ChatRoom> p) {

                ListCell<ChatRoom> cell = new ListCell<ChatRoom>(){

                    @Override
                    protected void updateItem(ChatRoom t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {

                            Label l = new Label(t.getRoomName());
                            l.setId("labelCell" + t.getRoomID());


                            System.out.println("Room : "+t.getRoomID() + "unread " + t.getUnreadMessages() );
                            if(t.getUnreadMessages() > 0)
                            {
                                l.getStyleClass().add("boldLabel");
                            }

                            setGraphic(l);
                        }
                    }
                };

                cell.setOnMouseClicked(e -> {
                    Label l = (Label) cell.getScene().lookup("#labelCell" + cell.getItem().getRoomID());
                    l.getStyleClass().remove("boldLabel");

                    try {
                        chatController.callSeenAPI(cell.getItem().getRoomID());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                return cell;
            }
        });


        handleItemClicked();
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    private void handleItemClicked() {

        ChatRoomListView.getSelectionModel().setSelectionMode(
                SelectionMode.SINGLE
        );

        ChatRoomListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ChatRoom> c) -> {
            int numberSelections = c.getList().toArray().length;

            if(numberSelections == 0)
            {
                chatController.messageAnchorPane.setVisible(false);
                chatController.setCurrentRoomID(0);
                chatController.setCurrentMessageNumber(0);
                chatController.messagePaneController = null;
            }
            else
            {
                ChatRoom item = ChatRoomListView.getSelectionModel().getSelectedItem();
                int roomID = item.getRoomID();

                if(roomID == chatController.getCurrentRoomID()) return;

                chatController.setCurrentMessageNumber(0);
                chatController.messageAnchorPane.setVisible(true);
                chatController.setCurrentRoomID(item.getRoomID());
                System.out.println("item.getRoomID()" + item.getRoomID());
                chatController.bindMessages();
            }
        });

    }
}
