package application;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static String socketMessage;

    public String removeDoubleCode(String string) {
        if (string.equals("null")) return "";
        return string.replace("\"", "");
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

    public void setExit(Stage stage){
        stage.setOnCloseRequest(e -> {
            e.consume(); // stop the event to do something before quitting
            closeRequest(stage); // method used to show a confirmation dialog
        });
    }

    public void setIconButton(JFXButton button, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        button.setGraphic(imageView);
    }

    public void setIconButton(ImageView imageView, String imagePath, int height, int width) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }

    public boolean isValidEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile( "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public String removeBracket(String string) {
        string = string.replace("[","");
        string = string.replace("]","");
        return string;
    }

    public static String getSocketMessage() {
        return socketMessage;
    }

    public static void setSocketMessage(String socketMessage) {
        Helper.socketMessage = socketMessage;
    }

}
