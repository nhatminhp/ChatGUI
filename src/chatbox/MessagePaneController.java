package chatbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import message.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagePaneController implements Initializable{

    @FXML private ListView<Message> listView;


    private ChatController chatController;

    private ObservableList<Message> listMessages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        listMessages = FXCollections.observableArrayList();

    }

    public void setListMessages(ObservableList<Message> _listMessages)
    {
//        listView = new ListView<Message>();
        listMessages =  _listMessages;
        FXCollections.reverse(listMessages);
        listView.setItems(listMessages);
        listView.setCellFactory(messageListView -> new MessageCell());

//        listView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
//            @Override
//            public ListCell<Message> call(ListView<Message> studentListView) {
//                System.out.println("haha1");
//                ListCell<Message> cell = new ListCell<Message>() {
//
//                    FXMLLoader fxmlLoader;
//                    HBox hbox;
//
//                    @Override
//                    protected void updateItem(Message message, boolean empty) {
//                        super.updateItem(message, empty);
//
//                        if (empty || message == null) {
//
//                            setText(null);
//                            setGraphic(null);
//
//                        } else {
//                            if (fxmlLoader == null) {
//                                if (message.getFromID() == ChatController.getCurrentUserID()) {
//                                    fxmlLoader = new FXMLLoader(getClass().getResource("MessageCellRight.fxml"));
//                                    MessageCellRightController c = fxmlLoader.getController();
//                                    c.setMessageCell(message);
//                                } else {
//                                    fxmlLoader = new FXMLLoader(getClass().getResource("MessageCellLeft.fxml"));
//                                    MessageCellLeftController c = fxmlLoader.getController();
//                                    c.setMessageCell(message);
//                                }
//
//                                try {
//                                    hbox = fxmlLoader.load();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
////            imageView = (ImageView) hbox.lookup("#imageView");
////            messageLabel = (Label) hbox.lookup("#messageLabel");
//
////                            Image i = new Image(getClass().getResourceAsStream("../images/profile.png"));
////                            imageView.setImage(i);
////                            messageLabel.setText(message.getMsg());
//
//                            setText(null);
//                            setGraphic(hbox);
//
//                            System.out.println("hahahahaah");
//                        }
//
//                    }
//                };
//                return cell;
//            }
//        });


//        listView.set

        System.out.println("hihihihihi");
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public void addNewMessage(Message m)
    {
        listMessages.add(m);
        listView.scrollTo(listMessages.size() - 1);
    }
}
