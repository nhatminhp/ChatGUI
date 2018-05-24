package chatbox;

import application.Connect;
import application.Helper;
import application.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextArea;
import myProfile.MyProfileController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private JFXButton ToMyProfileButton;
    @FXML
    private JFXButton SendButton;
    @FXML
    private JFXButton ExitButton;
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
    private TextArea MessagesTextArea;

    private String token;

    private Connect newConnect;

    private JsonNode newJson;

    private JsonNode profileJson;

    private JsonNode friendListJson;


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

    public void initialize() throws IOException {
        Helper helper = new Helper();
        System.out.println(callFriendListAPI());

        String returnedJson = callAPI();
        ObjectMapper mapper = new ObjectMapper();
        profileJson  = mapper.readTree(returnedJson);

        MyNameLabel.setText(helper.removeDoubleCode(profileJson.get("user_name").toString()));
    }

    @FXML
    private void clickToMyProfileButton(ActionEvent event) throws IOException {
        String returnedJson = callAPI();
        ObjectMapper mapper = new ObjectMapper();
        newJson  = mapper.readTree(returnedJson);                 //return data
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
            newStage.initStyle(StageStyle.UTILITY);
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
        newConnect.setURL("http://localhost:8080/get-my-profile");
        return newConnect.connect();
    }

    private String callFriendListAPI() throws IOException {
        Connect connect = new Connect();
        connect.addArgument("token", getToken());
        connect.setURL("http://localhost:8080/get-friend-list");
        return connect.connect();
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        // CODE
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
}
