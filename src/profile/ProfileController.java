package profile;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController implements Initializable{
	@FXML
	private JFXButton EditMyProfileButton;
	@FXML
	private ImageView MyProfileImage;
	@FXML
	private JFXButton EditProfileImageButton;
	@FXML
	private JFXButton SaveMyProfileButton;
	@FXML
	private JFXTextField MyUserNameTextField;
	@FXML
	private JFXTextField MyEmailTextField;
	@FXML
	private JFXTextField MyPhoneNumberTextField;
	@FXML
	private JFXTextField MyDOBTextField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	   // TODO (don't really need to do anything here).
	}
	
	public void editProfile(ActionEvent event) {
		System.out.println("Edit Button Clicked!");
		
		MyUserNameTextField.setEditable(true);
		MyEmailTextField.setEditable(true);
		MyPhoneNumberTextField.setEditable(true);
		MyDOBTextField.setEditable(true);
		
		EditMyProfileButton.setDisable(true);
		EditMyProfileButton.setVisible(false);
		SaveMyProfileButton.setDisable(false);
		SaveMyProfileButton.setVisible(true);
		
	}
	
	public File openFileChooser() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	public void editProfileImage(ActionEvent event) {
		System.out.println("Edit Image Button Clicked!");
		File file = openFileChooser();
		EditProfileImageButton.setDisable(true);
		
	}
}
