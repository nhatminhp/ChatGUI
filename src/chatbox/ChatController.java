package chatbox;

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

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private JFXButton ToMyProfileButton;
    @FXML
    private JFXButton SendButton;
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeChatTextField.setOnKeyPressed(event -> this.triggerEnter(event));
        ToMyProfileButton.setCursor(Cursor.HAND);
        SendButton.setCursor(Cursor.HAND);
    }

    @FXML
    private void clickToMyProfileButton(ActionEvent event) {
        loadConfirmCode();
    }

    private void loadConfirmCode() {
        System.out.println("To My Profile Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../editProfile/editProfile.fxml"));
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

    @FXML
    private void sendMessage(ActionEvent event) {
        // CODE
    }

    private Stage getStage() {
        return (Stage) ChatPane.getScene().getWindow();
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)  {
            clickToMyProfileButton(new ActionEvent());
        }
    }
}
