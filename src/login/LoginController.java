package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    private String username, password;

    @FXML
    private JFXButton LoginButton;
    @FXML
    private JFXCheckBox RememberMeCheckbox;
    @FXML
    private JFXTextField LoginUsernameTextField;
    @FXML
    private JFXPasswordField LoginPasswordField;
    @FXML
    private Hyperlink ForgetPassword;
    @FXML
    private Hyperlink SignUp;
    @FXML
    private AnchorPane LoginPane;
    @FXML
    private Label NoticeLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginUsernameTextField.setOnKeyPressed(event -> this.triggerEnter(event));
        LoginPasswordField.setOnKeyPressed(event -> this.triggerEnter(event));
        LoginButton.setCursor(Cursor.HAND);
        ForgetPassword.setCursor(Cursor.HAND);
    }

    private Stage getStage() {
        return (Stage) LoginPane.getScene().getWindow();
    }

    @FXML
    private void login(ActionEvent event) {
        System.out.println("Login Button Clicked!");

        //condition
        if (allFieldsFilled()){
            closeStage();
            loadChatbox();
        }
    }

    public void loadConfirmEmail(ActionEvent event) {
        System.out.println("Forget password hyperlink triggered!");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../confirm/confirm.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to confirm email scene.");
        }
    }

    public void signUp(ActionEvent event) {
        System.out.println("Sign up hyperlink triggered!");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../register/register.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Register scene.");
        }
    }

    private void closeStage() {
        ((Stage) LoginPane.getScene().getWindow()).close();
    }

    private void loadChatbox() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../chatbox/chatbox.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Chat Application");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            // Add log
        }
    }

    private void onInvalidLogin() {
        LoginUsernameTextField.setText(username);
        LoginPasswordField.setText("");
        NoticeLabel.setText("Incorrect Username or Password !");
    }

    private boolean allFieldsFilled() {
        if (LoginPasswordField.getText() == null || LoginPasswordField.getText().trim().isEmpty()
                || LoginUsernameTextField.getText() == null || LoginUsernameTextField.getText().trim().isEmpty()) {
            NoticeLabel.setText("You need to fill both username and password fields.");
            return false;
        }
        return true;
    }

    private void triggerEnter(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)  {
            login(new ActionEvent());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
