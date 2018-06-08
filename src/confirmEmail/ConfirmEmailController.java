package confirmEmail;

import application.Connect;
import application.Helper;
import application.Json;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import confirm.ConfirmController;
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


public class ConfirmEmailController implements Initializable {

    @FXML
    private JFXButton NextConfirmEmailButton;
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXTextField ConfirmEmailTextField;
    @FXML
    private Label NoticeError;
    @FXML
    private AnchorPane ConfirmEmailPane;

    private Connect newConnect;

    private Json newJson;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        newJson = new Json();

        Helper helper = new Helper();
        helper.setIconButton(ExitButton,"../images/exit.png");
        helper.setIconButton(BackButton,"../images/back.png");

        ConfirmEmailTextField.setOnKeyPressed(event -> this.triggerEnter(event));
        NextConfirmEmailButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        NoticeError.setText("");
    }

    private boolean fieldFilled(JFXTextField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }

    @FXML
    private void Exit(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void clickNextButton(ActionEvent event) throws IOException {
        NoticeError.setText("");
        if (!fieldFilled(ConfirmEmailTextField)) {
            NoticeError.setText("You need to enter your email first !");
        }
        else if (!isValidEmail(getEmail())) {
            NoticeError.setText("You need to enter valid email address");
        }
        else {
            String returnedJson = callAPI();
            newJson.setjson(returnedJson);
            Map<String, String> parse = newJson.getjson();
            newJson.listJson(parse);
            if (parse.get("success").equals("false")) {
                onFailed();
                return;
            }
            loadConfirmCode();
        }
    }

    private String callAPI() throws IOException {
        newConnect.addArgument("email", this.getEmail());
        newConnect.setURL("http://gossip-ict.tk/send-confirm-email");
        return newConnect.connect();
    }

    public String getEmail(){
        return ConfirmEmailTextField.getText();
    }

    private void onFailed() {
        NoticeError.setText("Unable to send to your entered email");
    }

    private void loadConfirmCode() {
        System.out.println("Next Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../confirm/confirm.fxml"));
            Parent root = loader.load();
            ConfirmController controller = loader.getController();
            controller.setEmail(this.getEmail());
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Confirm Code scene.");
        }
    }

    @FXML
    private void clickBackButton(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to scene.");
        }
    }

    private Stage getStage() {
        return (Stage) ConfirmEmailPane.getScene().getWindow();
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)  {
            try {
                clickNextButton(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmail(String email) {
        return (new Helper()).isValidEmail(email);
    }
}
