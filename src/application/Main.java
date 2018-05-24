package application;
	
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));

		Scene scene = new Scene(root);
//		stage.initStyle(StageStyle.UTILITY);
		stage.setFullScreen(true);
		stage.setScene(scene);
		stage.setTitle("Chat Application");

        Helper helper = new Helper();
        helper.setExit(stage);

		stage.show();


	}


	public static void main(String[] args) throws IOException {
		launch(args);
	}
}

