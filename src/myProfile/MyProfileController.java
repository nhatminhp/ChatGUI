package myProfile;

import com.jfoenix.controls.JFXButton;
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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;


public class MyProfileController implements Initializable {


    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ToEditProfileButton;
    @FXML
    private Label UsernameLabel;
    @FXML
    private Label PhoneNumberLabel;
    @FXML
    private Label EmailLabel;
    @FXML
    private Label DOBLabel;
    @FXML
    private ImageView MyImage;
    @FXML
    private AnchorPane ProfileInfoPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackButton.setCursor(Cursor.HAND);
        ToEditProfileButton.setCursor(Cursor.HAND);
    }

    @FXML
    private void clickBackButton(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../chatbox/chatbox.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to scene.");
        }
    }

    @FXML
    private void toEditProfile(ActionEvent event) {
        System.out.println("To Edit Profile Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../editProfile/editProfile.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to scene.");
        }
    }

    private Stage getStage() {
        return (Stage) ProfileInfoPane.getScene().getWindow();
    }


}
