package register;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXButton NextButton;
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXTextField NewAccUserName;
    @FXML
    private JFXTextField NewAccEmail;
    @FXML
    private JFXTextField NewAccPhoneNumber;
    @FXML
    private JFXDatePicker NewAccDOB;
    @FXML
    private JFXPasswordField NewAccPassword;
    @FXML
    private JFXPasswordField NewAccConfirmPassword;
    @FXML
    private AnchorPane RegisterPane;
    @FXML
    private Label NoticeRegisterError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NextButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        NoticeRegisterError.setText("");
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
        // CODE
        return true;
    }

    @FXML
    private void clickNextButton(ActionEvent event) {
        NoticeRegisterError.setText("");
        if (!allNecessaryFieldsFilled()) {
            NoticeRegisterError.setText("You need to enter all necessary fields");
        }
        else if (!isValidEmail(NewAccEmail.getText())) {
            NoticeRegisterError.setText("You need to enter valid email address");
        }
        else  loadConfirm();
    }


    private void loadConfirm() {
        System.out.println("Next Button pressed");
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
            System.out.println("Cannot switch to Confirm Code scene.");
        }
    }

    @FXML
    public void loadBackward(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
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

}
