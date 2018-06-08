package chatbox;

import application.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import myProfile.MyProfileController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    private AnchorPane MessagePane;
    @FXML
    private ListView MainListView;
    @FXML
    private TextArea MessagesTextArea;
    @FXML
    private VBox ListVBox;

    private String token;

    private Connect newConnect;

    private JsonNode newJson;

    private JsonNode profileJson;

    private JsonNode friendListJson;

    private JsonNode chatListJson;

    private int currentRoomID;

    private WebsocketConnection socketConnect;


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
        MessagesTextArea.setEditable(false);
        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Helper helper = new Helper();
        helper.setIconButton(ExitButton,"../images/exit.png");

    }

    public void initialize() throws IOException{
        try {
            socketConnect = new WebsocketConnection(new URI( "ws://gossip-ict.tk/websocket?token=" + getToken()));
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
        helper.setIconButton(MyProfileImage,"http://gossip-ict.tk/image/profile/"+profileJson.get("userID"),100,100);

        String returnedJson3 = callChatListAPI();
        ObjectMapper mapper3 = new ObjectMapper();
        chatListJson = mapper3.readTree(returnedJson3);
        ArrayNode arrayNode2 = (ArrayNode) chatListJson.get("room_list");
        Helper helper1 = new Helper();


        for (JsonNode jsonNode: arrayNode2
                ) {
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
            MainListView.getItems().add(roomName);
            ChatRoomList.addAChatRoomObject(chatRoom);
        }

        handleItemClicked();

        ListVBox.getChildren().addAll(MainListView);
    }

    private void handleItemClicked() {
        MainListView.setOnMouseClicked(event ->
        {
            //
            String item = MainListView.getSelectionModel().getSelectedItems().toString();
            ChatRoom clickedChatRoom = (new ChatRoomList()).searchRoom((new Helper()).removeBracket(item));
            setCurrentRoomID(clickedChatRoom.getRoomID());
            System.out.println(clickedChatRoom.getRoomID());
        });
    }

    @FXML
    private void sendMessage(ActionEvent event) throws URISyntaxException {
        String message = "{\"roomID\":"+ this.getCurrentRoomID() + ",\"message\":\"" + TypeChatTextField.getText() + "\"}";
        socketConnect.send(message);
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

    private String callAPI () throws IOException {
        newConnect.addArgument("token", getToken());
        newConnect.setURL("http://gossip-ict.tk/get-my-profile");
        return newConnect.connect();
    }

    private String callFriendListAPI() throws IOException {
        Connect connect = new Connect();
        connect.addArgument("token", getToken());
        connect.setURL("http://gossip-ict.tk/get-friend-list");
        return connect.connect();
    }

    private String callChatListAPI() throws IOException {
        Connect connect = new Connect();
        connect.addArgument("token", getToken());
        connect.setURL("http://gossip-ict.tk/get-chat-room-list");
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
}
