package editProfile;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.Connect;
import application.Helper;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myProfile.MyProfileController;

public class EditProfileController implements Initializable{

	@FXML
	private JFXButton BackButton;
    @FXML
    private JFXButton ExitButton;
	@FXML
	private ImageView MyEditProfileImage;
	@FXML
	private JFXButton EditProfileImageButton;
	@FXML
	private JFXButton SaveMyProfileButton;
	@FXML
	private JFXTextField EditUserNameTextField;
	@FXML
	private JFXTextField EditEmailTextField;
	@FXML
	private JFXTextField EditPhoneNumberTextField;
	@FXML
	private JFXDatePicker MyDOBDatePicker;
	@FXML
	private AnchorPane MyEditProfilePane;

	private Connect newConnect;

    private JsonNode returnedJson;

    private String token;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    Helper helper = new Helper();
        helper.setIconButton(BackButton,"../images/back.png");
        helper.setIconButton(ExitButton,"../images/exit.png");

        BackButton.setCursor(Cursor.HAND);
        SaveMyProfileButton.setCursor(Cursor.HAND);
        EditProfileImageButton.setCursor(Cursor.HAND);
    }

    public void initialize(JsonNode json) {
        Helper helper = new Helper();
        String user_name = helper.removeDoubleCode(json.get("user_name").toString());
        String email = helper.removeDoubleCode(json.get("email").toString());
        String phone_number = helper.removeDoubleCode(json.get("phone_number").toString());
        String DOB = helper.removeDoubleCode(json.get("DOB").toString());
        EditUserNameTextField.setText(user_name);
        EditEmailTextField.setText(email);
        EditPhoneNumberTextField.setText(phone_number);
        if (!DOB.equals("")){
            String[] parts = DOB.split("-");
            System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);
            //MyDOBDatePicker.setValue(LocalDate.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
        }
    }

    @FXML
    private void Exit(ActionEvent event) {
        this.getStage().close();
    }

	@FXML
	private void clickBackButton(ActionEvent event) {
		System.out.println("Back Button clicked");

		loadBackward();
	}

	@FXML
	private void clickSaveButton(ActionEvent event) {
		System.out.println("Save Button clicked");

		loadMyProfile();
	}

	private void loadMyProfile() {

	}

	private void loadBackward() {
		System.out.println("Back Button pressed");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../myProfile/myProfile.fxml"));
			Parent root = loader.load();

            MyProfileController controller = loader.getController();
            controller.setReturnedJson(this.returnedJson);
            controller.setToken(this.token);
            controller.initialize(this.returnedJson);

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
	private void editProfileImage(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files",
						"*.bmp", "*.png", "*.jpg", "*.jpeg"));
		chooser.setTitle("Open File");
		File file = chooser.showOpenDialog(new Stage());
		if(file != null) {
			String imagePath = file.toURI().toString();
			System.out.println(imagePath);
			Image image = new Image(imagePath);
			MyEditProfileImage.setImage(image);
        }
		else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("You haven't choose any image");
			alert.showAndWait();
		}
	}

	public void setToken(String token) {
	    this.token = token;
    }

	private Stage getStage() {
		return (Stage) MyEditProfilePane.getScene().getWindow();
	}

    public void setReturnedJson(JsonNode newJson) {
        this.returnedJson = newJson;
    }

}
