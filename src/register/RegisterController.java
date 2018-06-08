package register;

import application.Connect;
import application.Helper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXButton NextButton;
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXTextField NewAccUserName;
    @FXML
    private JFXTextField NewAccEmail;
    @FXML
    private JFXPasswordField NewAccPassword;
    @FXML
    private JFXPasswordField NewAccConfirmPassword;
    @FXML
    private AnchorPane RegisterPane;
    @FXML
    private Label NoticeRegisterError;
    @FXML
    private Label UsernameError;
    @FXML
    private Label EmailError;
    @FXML
    private Label PasswordError;
    @FXML
    private Label ConfirmPasswordError;

    private Connect connect;

    private JsonNode registerJson;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewAccUserName.setOnKeyPressed(event -> {
            try {
                triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        NewAccEmail.setOnKeyPressed(event -> {
            try {
                triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        NewAccPassword.setOnKeyPressed(event -> {
            try {
                triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        NewAccConfirmPassword.setOnKeyPressed(event -> {
            try {
                triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            connect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        NextButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        Helper helper = new Helper();
        helper.setIconButton(ExitButton,"../images/exit.png");
        helper.setIconButton(BackButton,"../images/back.png");
    }

    private boolean allNecessaryFieldsFilled() {
        return (fieldFilled(NewAccUserName) && fieldFilled(NewAccEmail) && passwordFieldFilled(NewAccPassword) && passwordFieldFilled(NewAccConfirmPassword));
    }

    private boolean fieldFilled(JFXTextField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }

    private boolean passwordFieldFilled(JFXPasswordField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }

    private boolean isValidEmail(String email) {
        return (new Helper()).isValidEmail(email);
    }

    @FXML
    private void clickNextButton(ActionEvent event) throws IOException {
        NoticeRegisterError.setText("");
        if (!allNecessaryFieldsFilled()) {
            NoticeRegisterError.setText("You need to fill in all fields");
        }
        else {
            String returnedJson = callConnectAPI();
            ObjectMapper mapper = new ObjectMapper();
            registerJson = mapper.readTree(returnedJson);
            if (registerJson.get("success").asBoolean()) {
                loadConfirm();
                return;
            }
            System.out.println(registerJson.get("error_message").toString());
            if (registerJson.get("error_message").toString().contains("Email already existed")){
                EmailError.setText("Email already existed");
            }
            if (registerJson.get("error_message").toString().contains("Nickname must have less than 255 characters")){
                UsernameError.setText("Nickname must have less than 255 characters");
            }
            if (registerJson.get("error_message").toString().contains("Password must contain")){
                PasswordError.setText("Password must contain at least eight characters, at least \none number and both lower and uppercase letters and special characters");
            }
            if (!isValidEmail(NewAccEmail.getText())) {
                EmailError.setText("Email input has wrong form");
            }
            if (!NewAccPassword.getText().equals(NewAccConfirmPassword.getText())) {
                ConfirmPasswordError.setText("Confirm password does not match");
            }
        }
    }


    private void loadConfirm() {
        System.out.println("Next Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../confirm/confirm.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Confirm Code scene.");
        }
    }

    private String callConnectAPI () throws IOException {
        connect.addArgument("name", NewAccUserName.getText());
        connect.addArgument("email", NewAccEmail.getText());
        connect.addArgument("password", NewAccPassword.getText());
        connect.addArgument("confirm_password", NewAccConfirmPassword.getText());
        connect.setURL("http://gossip-ict.tk/signup");
        return connect.connect();
    }

    @FXML
    public void loadBackward(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();

        } catch (Exception e) {
            System.out.println("Cannot switch backward to Login scene.");
        }
    }

    private Stage getStage() {
        return (Stage) RegisterPane.getScene().getWindow();
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)  {
            clickNextButton(new ActionEvent());
        }
    }
}
