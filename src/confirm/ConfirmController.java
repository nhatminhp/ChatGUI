package confirm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {

    @FXML
    private JFXButton ConfirmCodeButton;
    @FXML
    private JFXTextField ConfirmCodeField;
    @FXML
    private Label NoticeCodeError;
    @FXML
    private AnchorPane ConfirmCodePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfirmCodeField.setOnKeyPressed(event -> this.triggerEnter(event));
        ConfirmCodeButton.setCursor(Cursor.HAND);
        NoticeCodeError.setText("");
    }


    private Stage getStage() {
        return (Stage) ConfirmCodePane.getScene().getWindow();
    }

    private boolean isCorrectCode(String code) {
        // CODE
        return true;
    }

    @FXML
    private void clickConfirmCodeButton(ActionEvent event) {
        NoticeCodeError.setText("");
        if (!fieldFilled(ConfirmCodeField)) {
            NoticeCodeError.setText("You need to enter the code first !");
        } else if (!isCorrectCode(ConfirmCodeField.getText())) {
            NoticeCodeError.setText("Wrong code !");
        } else {
            loadLogin();
        }
    }

    private void loadLogin() {
        System.out.println("Confirm Button pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Login scene.");
        }
    }

    private boolean fieldFilled(JFXTextField textField) {
        return (textField.getText() != null && !textField.getText().trim().isEmpty());
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)  {
            clickConfirmCodeButton(new ActionEvent());
        }
    }
}