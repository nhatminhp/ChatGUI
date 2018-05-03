package editProfile;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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

public class EditProfileController implements Initializable{

	@FXML
	private JFXButton BackButton;
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
	private JFXTextField MyDOBTextField;
	@FXML
	private AnchorPane MyEditProfilePane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BackButton.setCursor(Cursor.HAND);
		SaveMyProfileButton.setCursor(Cursor.HAND);
		EditProfileImageButton.setCursor(Cursor.HAND);
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
		System.out.println("To My Profile Button pressed");
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
	private void editProfileImage(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files",
						"*.bmp", "*.png", "*.jpg", "*.jpeg"));
		chooser.setTitle("Open File");
		File file = chooser.showOpenDialog(new Stage());
		if(file != null) {
			String imagePath = file.toURI().toString();
			System.out.println("file:"+imagePath);
			Image image = new Image(imagePath);
			MyEditProfileImage.setImage(image);
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("You haven't choose any image");
			alert.showAndWait();
		}
	}

	private Stage getStage() {
		return (Stage) MyEditProfilePane.getScene().getWindow();
	}
}
