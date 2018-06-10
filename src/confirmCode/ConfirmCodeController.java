package confirmCode;

import application.Connect;
import application.Helper;
import application.Json;
import chatbox.ChatController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ConfirmCodeController implements Initializable {

    @FXML
    private JFXButton ConfirmCodeButton;
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXTextField ConfirmCodeField;
    @FXML
    private Label ConfirmCodeError;
    @FXML
    private AnchorPane ConfirmCodePane;

    private Connect newConnect;

    private Json newJson;

    private String email;

    private String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        newJson = new Json();

        ConfirmCodeField.setOnKeyPressed(event -> {
            try {
                this.triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Helper helper = new Helper();
        helper.setIconButton(BackButton,"../images/back.png");
        helper.setIconButton(ExitButton,"../images/exit.png");

        ConfirmCodeButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        ExitButton.setCursor(Cursor.HAND);
        ConfirmCodeError.setText("");
    }

    @FXML
    private void Exit(ActionEvent event) {
        this.getStage().close();
    }


    private Stage getStage() {
        return (Stage) ConfirmCodePane.getScene().getWindow();
    }

    private void onFailed() {
        ConfirmCodeError.setText("Wrong confirmation code");
    }

    @FXML
    private void clickConfirmCodeButton(ActionEvent event) throws IOException {
        ConfirmCodeError.setText("");
        if (!fieldFilled(ConfirmCodeField)) {
            ConfirmCodeError.setText("You need to fill in above field");
        } else {
            String returnedJson = callConfirmAPI();
            newJson.setjson(returnedJson);
            Map<String, String> parse = newJson.getjson();
            newJson.listJson(parse);
            if (parse.get("success").equals("false")) {
                onFailed();
                return;
            }
            loadChatbox();
        }
    }

    private void loadChatbox() {
        System.out.println("Confirm Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatbox/chatbox.fxml"));
            Parent root = loader.load();
            ChatController controller = loader.getController();

            Helper.setChatController(controller);

            controller.setToken(this.getToken());
            controller.initialize();
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Login scene");
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    private String callConfirmAPI() throws IOException {
        newConnect.addArgument("email", getEmail());
        newConnect.addArgument("confirmation_code", ConfirmCodeField.getText());
        newConnect.setURL("http://gossip-ict.tk:8080/check-confirmation-code");
        return newConnect.connect();
    }


    @FXML
    private void clickBackButton(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to scene.");
        }
    }

    private boolean fieldFilled(JFXTextField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }


    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)  {
            clickConfirmCodeButton(new ActionEvent());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}