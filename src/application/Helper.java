package application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Helper {
    public String removeDoubleCode(String string) {
        if (string.equals("null")) return "";
        return string.replace("\"", "");
    }

    public void closeRequest(Stage stage){

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
}
