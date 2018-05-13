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
import java.net.MalformedURLException;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));

		Scene scene = new Scene(root);
		stage.initStyle(StageStyle.UTILITY);
		stage.setScene(scene);
		stage.setTitle("Chat Application");

		stage.setOnCloseRequest(e -> {
			e.consume(); // stop the event to do something before quitting
			closeRequest(stage); // method used to show a confirmation dialog
		});

		stage.show();


	}

	private void closeRequest(Stage stage){

		String msg = "Do you really want to quit ?";
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.initStyle(StageStyle.DECORATED);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(stage);
		alert.getDialogPane().setContentText(msg);
		alert.getDialogPane().setHeaderText(null);

		alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> { stage.close(); }); // then we need to call the close method for a stage, if the response is ok.

	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}

