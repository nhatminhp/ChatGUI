package chatbox;

import application.Helper;
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

    }

    public void setListMessages(ObservableList<Message> _listMessages)
    {

        listMessages =  normalize(_listMessages);
        FXCollections.reverse(listMessages);
        listView.setItems(listMessages);
        listView.setCellFactory(messageListView -> new MessageCell());

    }

    public ObservableList<Message> normalize(ObservableList<Message> listMessages) {
        Helper helper = new Helper();
        for (Message message: listMessages) {
            message.setMsg(helper.normalize(message.getMsg()));
        }
        return  listMessages;
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
