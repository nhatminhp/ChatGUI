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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NextButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
    }

    public boolean allNecessaryFieldsFilled() {
        return true;
    }

    public void loadConfirm(ActionEvent event) {
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

    public void loadBackward(ActionEvent event) {
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
