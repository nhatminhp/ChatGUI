package changePassword;

import application.Connect;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXButton ChangePasswordButton;
    @FXML
    private JFXPasswordField OldPasswordField;
    @FXML
    private JFXPasswordField NewPasswordField;
    @FXML
    private JFXPasswordField NewConfirmPasswordField;
    @FXML
    private Label NoticeError;
    @FXML
    private Label OldPasswordError;
    @FXML
    private Label NewPasswordError;
    @FXML
    private Label ConfirmPasswordError;

    private Connect newConnect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BackButton.setCursor(Cursor.HAND);
        ExitButton.setCursor(Cursor.HAND);
        ChangePasswordButton.setCursor(Cursor.HAND);
    }


}
