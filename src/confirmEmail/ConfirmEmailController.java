package confirmEmail;

import com.jfoenix.controls.JFXButton;
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
import javafx.stage.StageStyle;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.net.URL;
import java.util.ResourceBundle;


public class ConfirmEmailController implements Initializable {

    @FXML
    private JFXButton NextConfirmEmailButton;
    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXTextField ConfirmEmailTextField;
    @FXML
    private Label NoticeError;
    @FXML
    private AnchorPane ConfirmEmailPane;

    private static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfirmEmailTextField.setOnKeyPressed(event -> this.triggerEnter(event));
        NextConfirmEmailButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        NoticeError.setText("");
    }

    private boolean fieldFilled(JFXTextField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }

    @FXML
    private void clickNextButton(ActionEvent event) {
        NoticeError.setText("");
        if (!fieldFilled(ConfirmEmailTextField)) {
            NoticeError.setText("You need to enter your email first !");
        }
//        else if (!isValidEmail(NoticeError.getText())) {
//            NoticeError.setText("You need to enter valid email address");
//        }
        else loadConfirmCode();
    }

    private void loadConfirmCode() {
        System.out.println("Next Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../confirm/confirm.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
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
            newStage.initStyle(StageStyle.UTILITY);
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
            clickNextButton(new ActionEvent());
        }
    }
}
