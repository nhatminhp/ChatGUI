package myProfile;

import application.Connect;
import application.Helper;
import chatbox.ChatController;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import confirmEmail.ConfirmEmailController;
import editProfile.EditProfileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;


public class MyProfileController implements Initializable {


    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXButton SignOutButton;
    @FXML
    private JFXButton ChangePasswordButton;
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
    private ImageView MyImageView;
    @FXML
    private AnchorPane ProfileInfoPane;

    private String token;

    private JsonNode returnedJson;

    private Connect newConnect;

    private JsonNode newJson;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackButton.setCursor(Cursor.HAND);
        ToEditProfileButton.setCursor(Cursor.HAND);

        Helper helper = new Helper();
        helper.setIconButton(BackButton,"../images/back.png");
        helper.setIconButton(ExitButton,"../images/exit.png");
        helper.setIconButton(SignOutButton,"../images/signout.jpg");
    }

    public void initialize(JsonNode json) {
        Helper helper = new Helper();
        helper.setIconButton(MyImageView,"../images/profile.png",300,300);
        String user_name = helper.removeDoubleCode(json.get("user_name").toString());
        String email = helper.removeDoubleCode(json.get("email").toString());
        String phone_number = helper.removeDoubleCode(json.get("phone_number").toString());
        String DOB = helper.removeDoubleCode(json.get("DOB").toString());
        UsernameLabel.setText(user_name);
        EmailLabel.setText(email);
        PhoneNumberLabel.setText(phone_number);
        DOBLabel.setText(DOB);
    }

    @FXML
    private void Exit(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void signOut(ActionEvent event) {
        System.out.println("signout button clicked");
        (new ConfirmEmailController()).clickBackButton(new ActionEvent());
    }


    @FXML
    private void clickBackButton(ActionEvent event) {
        System.out.println("Back Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatbox/chatbox.fxml"));
            Parent root = loader.load();

            ChatController controller = loader.getController();

            Helper.setChatController(controller);

            controller.setToken(getToken());
            controller.initialize();

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

    @FXML
    private void toEditProfile(ActionEvent event) throws IOException {
        System.out.println("To Edit Profile Button pressed");
        loadEditProfile();
    }

    private void loadEditProfile() throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../editProfile/editProfile.fxml"));
            Parent root = loader.load();

            EditProfileController controller = loader.getController();
            controller.setReturnedJson(returnedJson);
            controller.setToken(getToken());
            controller.initialize(returnedJson);

            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JsonNode getReturnedJson() {
        return returnedJson;
    }

    public void setReturnedJson(JsonNode returnedJson) {
        this.returnedJson = returnedJson;
    }


    private Stage getStage() {
        return (Stage) ProfileInfoPane.getScene().getWindow();
    }


}
