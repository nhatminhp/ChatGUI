package chatbox;

import application.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import message.Message;
import myProfile.MyProfileController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private JFXButton ToMyProfileButton;
    @FXML
    private JFXButton SendButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXButton ShowFriendRequestsButton;
    @FXML
    private JFXButton SearchFriendButton;
    @FXML
    private JFXTextArea TypeChatTextField;
    @FXML
    private Label MyNameLabel;
    @FXML
    private ImageView MyProfileImage;
    @FXML
    private AnchorPane ChatPane;

    @FXML
    private Pane MessagePane;
    @FXML
    private ListView<ChatRoom> MainListView;

    @FXML
    private VBox ListVBox;

    @FXML private AnchorPane messageAnchorPane;

    @FXML private Button newButton;

    @FXML Hyperlink loadOldMessagesLink;

    private String token;

    private Connect newConnect;

    private JsonNode newJson;

    private JsonNode profileJson;

    private JsonNode friendListJson;

    private JsonNode chatListJson;

    private int currentRoomID;

    private static int currentUserID;

    private int currentMessageNumber;

    private WebsocketConnection socketConnect;

    private MessagePaneController messagePaneController;


    ///////////////
    ObservableList<Message> list;
    int currentMessageOrder;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeChatTextField.setOnKeyPressed(event -> {
            try {
                this.triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ToMyProfileButton.setCursor(Cursor.HAND);
        SendButton.setCursor(Cursor.HAND);

        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Helper helper = new Helper();
        helper.setIconButton(ExitButton,"../images/exit.png");

        handleLoadOldMessage();
    }

    public void loadMessagePane()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessagePane.fxml"));
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessagePane.getChildren().add(pane);

    }

    public void initialize() throws IOException{
        try {
            socketConnect = new WebsocketConnection(new URI( "ws://localhost:8080/websocket?token=" + getToken()));
            socketConnect.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Helper helper = new Helper();
        String returnedJson1 = callFriendListAPI();
        ObjectMapper mapper1 = new ObjectMapper();
        friendListJson = mapper1.readTree(returnedJson1);
        ArrayNode arrayNode1 = (ArrayNode) friendListJson.get("friend_list");
        for (JsonNode jsonNode: arrayNode1
             ) {
            addToFriendList(jsonNode);
        }

        String returnedJson2 = callAPI();
        ObjectMapper mapper2 = new ObjectMapper();
        profileJson  = mapper2.readTree(returnedJson2);
        MyNameLabel.setText(helper.removeDoubleCode(profileJson.get("user_name").toString()));
        setCurrentUserID(profileJson.get("userID").asInt());
//        helper.setIconButton(MyProfileImage,"http://localhost:8080/image/profile/"+profileJson.get("userID"),100,100);

        String returnedJson3 = callChatListAPI();
        ObjectMapper mapper3 = new ObjectMapper();
        chatListJson = mapper3.readTree(returnedJson3);
        ArrayNode arrayNode2 = (ArrayNode) chatListJson.get("room_list");
        Helper helper1 = new Helper();


        ObservableList<ChatRoom> listChatRoom = FXCollections.observableArrayList();
        for (JsonNode jsonNode: arrayNode2) {
            ChatRoom chatRoom = new ChatRoom(jsonNode.get("roomID").asInt());
            chatRoom.setLastMessage(jsonNode.get("last_message").toString());
            chatRoom.setSendingTime(jsonNode.get("sending_time").toString());
            chatRoom.setUnreadMessages(jsonNode.get("unread_message").asInt());
            ArrayNode userNodes = (ArrayNode) jsonNode.get("user_list");
            String roomName = "";
            for (JsonNode userNode: userNodes
                 ) {
                roomName = roomName + helper.removeDoubleCode(userNode.get("user_name").toString()) + ", ";
                User user = new User(userNode.get("userID").asInt(), userNode.get("user_name").toString());
                chatRoom.addUserObject(user);
            }
            roomName = roomName.substring(0, roomName.length() - 2);
            chatRoom.setRoomName(roomName);
            System.out.println(chatRoom.getRoomID() + "," + chatRoom.getRoomName());

            listChatRoom.add(chatRoom);

            ChatRoomList.addAChatRoomObject(chatRoom);
        }

        MainListView.setCellFactory(new Callback<ListView<ChatRoom>, ListCell<ChatRoom>>(){

            @Override
            public ListCell<ChatRoom> call(ListView<ChatRoom> p) {

                ListCell<ChatRoom> cell = new ListCell<ChatRoom>(){

                    @Override
                    protected void updateItem(ChatRoom t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {

                            Label l = new Label(t.getRoomName());
                            l.setId("labelCell" + t.getRoomID());

//                            l.setOnMouseClicked(e -> {
//
//                                l.getStyleClass().remove("boldLabel");
//                            });

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
                });

                return cell;
            }
        });

        MainListView.setItems(listChatRoom);

        handleItemClicked();

        ListVBox.getChildren().addAll(MainListView);

        handleNewButton();
    }

    private void handleItemClicked() {

        MainListView.getSelectionModel().setSelectionMode(
                SelectionMode.SINGLE
        );

        MainListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ChatRoom> c) -> {
            int numberSelections = c.getList().toArray().length;

//            System.out.println("select : "+numberSelections);
            if(numberSelections == 0)
            {
                messageAnchorPane.setVisible(false);
                setCurrentRoomID(0);
                setCurrentMessageNumber(0);
                messagePaneController = null;
            }
            else
            {
                ChatRoom item = MainListView.getSelectionModel().getSelectedItem();
                int roomID = item.getRoomID();

                if(roomID == getCurrentRoomID()) return;

                setCurrentMessageNumber(0);
                messageAnchorPane.setVisible(true);
                setCurrentRoomID(item.getRoomID());
                System.out.println("item.getRoomID()" + item.getRoomID());
                bindMessages();
                System.out.println("After bindMessage");
            }
        });

    }


    private void bindMessages()
    {
        FXMLLoader messagePaneLoader = new FXMLLoader(getClass().getResource("MessagePane.fxml"));
        Pane p = null;
        try {
            p = messagePaneLoader.load();
            messagePaneController = messagePaneLoader.getController();
            messagePaneController.setListMessages(getMessages(getCurrentRoomID()));
            messagePaneController.setChatController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessagePane.getChildren().add(p);
    }

    @FXML
    private void sendMessage(ActionEvent event) throws URISyntaxException {
        String message = "{\"roomID\":"+ this.getCurrentRoomID() + ",\"message\":\"" + TypeChatTextField.getText() + "\"}";
        socketConnect.send(message);


        Message sentMessage = new Message(getCurrentRoomID(), getCurrentUserID(), TypeChatTextField.getText(), Double.toString(System.currentTimeMillis()));
        System.out.println("sent Message : " + sentMessage.toString());
        pushNewMessage(sentMessage);

        setCurrentMessageNumber(getCurrentMessageNumber() + 1);



        TypeChatTextField.setText("");
    }

    private void addToFriendList(JsonNode jsonNode) {
        Friend friend = new Friend();
        friend.setUserID(jsonNode.get("userID").toString());
        friend.setUsername(jsonNode.get("user_name").toString());
        friend.setEmail(jsonNode.get("email").toString());
        friend.setPhoneNumber(jsonNode.get("phone_number").toString());
        friend.setDOB(jsonNode.get("DOB").toString());
        friend.setProfileImage(jsonNode.get("profile_picture").toString());
        friend.setFavorite(jsonNode.get("user_name").asBoolean());

        FriendList.addFriendObject(friend);
    }

    @FXML
    private void Exit(ActionEvent event){
        this.getStage().close();
    }

    @FXML
    private void clickToMyProfileButton(ActionEvent event) throws IOException {
        String returnedJson = callAPI();
        ObjectMapper mapper = new ObjectMapper();
        newJson  = mapper.readTree(returnedJson);
        System.out.println(newJson);
        if (newJson.get("success").asBoolean() && newJson.get("verify_token").asBoolean()) {
            loadMyProfile(newJson);
        }
    }

    private void loadMyProfile(JsonNode returnedJson) {
        System.out.println("To My Profile Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../myProfile/myProfile.fxml"));
            Parent root = loader.load();

            MyProfileController controller = loader.getController();
            controller.setToken(getToken());
            controller.setReturnedJson(returnedJson);
            controller.initialize(returnedJson);

            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to scene.");
        }
    }

    private ObservableList<Message> getMessages(int roomID)
    {
        ObservableList<Message> listMessages = FXCollections.observableArrayList();

        String returnedMessage = callGetMessagesAPI(roomID);
        System.out.println("return message : "+returnedMessage);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(returnedMessage);
            ArrayNode arrayNode = (ArrayNode) jsonNode.get("message_list");
            for (JsonNode node: arrayNode) {

                Message m = Helper.parseMessage(node, roomID);

                listMessages.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("after getMesssages");
        System.out.println();
        return listMessages;
    }

    private String callGetMessagesAPI(int roomID){
        Connect connect = null;
        try {
            connect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        connect.addArgument("roomID", Integer.toString(roomID));
        connect.addArgument("token", getToken());
        connect.addArgument("number_of_messages", Integer.toString(getCurrentMessageNumber()+10));
        setCurrentMessageNumber(getCurrentMessageNumber() + 10);

        try {
            connect.setURL("http://localhost:8080/load-message");
            return connect.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String callAPI () throws IOException {
        newConnect.addArgument("token", getToken());
        newConnect.setURL("http://localhost:8080/get-my-profile");
        return newConnect.connect();
    }

    private String callFriendListAPI() throws IOException {
        Connect connect = new Connect();
        connect.addArgument("token", getToken());
        connect.setURL("http://localhost:8080/get-friend-list");
        return connect.connect();
    }

    private String callChatListAPI() throws IOException {
        Connect connect = new Connect();
        connect.addArgument("token", getToken());
        connect.setURL("http://localhost:8080/get-chat-room-list");
        return connect.connect();
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    private Stage getStage() {
        return (Stage) ChatPane.getScene().getWindow();
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)  {
            clickToMyProfileButton(new ActionEvent());
        }
    }

    public int getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(int currentRoomID) {
        this.currentRoomID = currentRoomID;
    }

    public int getCurrentMessageNumber() {
        return currentMessageNumber;
    }

    public void setCurrentMessageNumber(int currentMessageNumber) {
        this.currentMessageNumber = currentMessageNumber;
    }
    public static int getCurrentUserID() {
        return currentUserID;
    }

    public static void setCurrentUserID(int _currentUserID) {
        currentUserID = _currentUserID;
    }









    private ObservableList<Message> testmessages()
    {
        list = FXCollections.observableArrayList();

        list.add(new Message(1, 10, "1", "2018-06-10 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "2", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, 10, "3", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "4", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "5", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, 10, "6", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, 10, "7", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "8", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "9", "2018-06-11 09:42:23.0"));

        list.add(new Message(1, 10, "10", "2018-06-12 09:42:23.0"));

        list.add(new Message(1, getCurrentUserID(), "11", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "12", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, 10, "13", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, 10, "14", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "15", "2018-06-11 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "16", "2018-06-12 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "17", "2018-06-13 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "18", "2018-06-14 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "19", "2018-06-16 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "20", "2018-06-17 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "21", "2018-06-19 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "22", "2018-06-10 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "23", "2018-06-21 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "24", "2018-06-21 09:42:23.0"));
        list.add(new Message(1, getCurrentUserID(), "25", "2018-06-13 09:42:23.0"));
        currentMessageOrder = 25;

        return list;
    }


    private void handleNewButton()
    {
        newButton.setOnAction(e -> {
            currentMessageOrder += 1;
            messagePaneController.addNewMessage(new Message(1, getCurrentUserID(), Integer.toString(currentMessageOrder), "2018-06-13 09:42:23.0"));
        });
    }

    public void pushNewMessage(Message m)
    {
        if(m.getRoomID() == currentRoomID)
        {
            messagePaneController.addNewMessage(m);
            setCurrentMessageNumber(getCurrentMessageNumber() + 1);
        }
        else
        {

        }
    }

    private void handleLoadOldMessage()
    {
        loadOldMessagesLink.setOnMouseClicked(e -> {
            System.out.println("Clicked hyper link");
            bindMessages();
        });
    }
}
