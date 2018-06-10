package login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import application.Connect;
import application.Helper;
import application.Json;
import chatbox.ChatController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginController implements Initializable {

    private Connect newConnect;
    private Json newJson;

    @FXML
    private JFXButton LoginButton;
    @FXML
    private JFXButton ExitButton;
    @FXML
    private JFXCheckBox RememberMeCheckbox;
    @FXML
    private JFXTextField LoginUsernameTextField;
    @FXML
    private JFXPasswordField LoginPasswordField;
    @FXML
    private Hyperlink ForgetPassword;
    @FXML
    private Hyperlink SignUp;
    @FXML
    private AnchorPane LoginPane;
    @FXML
    private Label NoticeLabel;
    @FXML
    private ImageView AppImage;

    private String token;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newJson = new Json();
        try {
            newConnect = new Connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        LoginUsernameTextField.setOnKeyPressed(event -> {
            try {
                this.triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        LoginPasswordField.setOnKeyPressed(event -> {
            try {
                this.triggerEnter(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        LoginButton.setCursor(Cursor.HAND);
        ForgetPassword.setCursor(Cursor.HAND);
        (new Helper()).setIconButton(ExitButton,"../images/exit.png");
        (new Helper()).setIconButton(AppImage,"../images/AppIcon.png",300,300);
    }

    private Stage getStage() {
        return (Stage) LoginPane.getScene().getWindow();
    }

    @FXML
    private void Exit(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        System.out.println("Login Button Clicked!");

        //condition
        if (allFieldsFilled()){

            String returnedJson = callAPI();
            newJson.setjson(returnedJson);
            Map<String,String> parse = newJson.getjson();
            newJson.listJson(parse);
            ///////////////////////////////////////////////////
            if (parse.get("success").equals("false")) {
                onInvalidLogin();
                return;
            }if (parse.get("success").equals("true") && parse.get("confirm").equals("false")) {
                onNotConfirmed();
                return;
            }
            setToken(parse.get("token"));
            if (RememberMeCheckbox.isSelected()) {
                rememberLogin();
            }
            loadChatbox();
        }
    }

    private void rememberLogin() {
        String info = LoginUsernameTextField.getText()+"\n"+LoginPasswordField.getText();
        PrintWriter out = null;
        try {
            out = new PrintWriter("info.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(info);
    }


    private String callAPI () throws IOException {
        newConnect.addArgument("email", LoginUsernameTextField.getText());
        newConnect.addArgument("password", LoginPasswordField.getText());
        newConnect.setURL("http://gossip-ict.tk:8080/login");
        return newConnect.connect();
    }

    @FXML
    public void loadConfirmEmail(ActionEvent event) {
        System.out.println("Forget password hyperlink triggered!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../confirmEmail/confirmEmail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application.");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to confirm email scene.");
        }
    }

    @FXML
    public void signUp(ActionEvent event) {
        System.out.println("Sign up hyperlink triggered!");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../register/register.fxml"));
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();
        } catch (Exception e) {
            System.out.println("Cannot switch to Register scene.");
        }
    }


    private void loadChatbox(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatbox/chatbox.fxml"));
            Parent root = loader.load();
            ChatController controller = loader.getController();

            Helper.setChatController(controller);

            controller.setToken(this.getToken());
            controller.initialize();

            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setFullScreen(true);
            newStage.setTitle("Chat Application");
            newStage.setScene(scene);
            newStage.show();

            this.getStage().close();

        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    private void onInvalidLogin() {
        NoticeLabel.setText("Incorrect Username or Password !");
    }

    private void onNotConfirmed() {
        NoticeLabel.setText("You haven't confirmed email, click forgot password to confirm your email");
    }

    private boolean allFieldsFilled() {
        if (LoginPasswordField.getText() == null || LoginPasswordField.getText().trim().isEmpty()
                || LoginUsernameTextField.getText() == null || LoginUsernameTextField.getText().trim().isEmpty()) {
            NoticeLabel.setText("You need to fill both username and password fields.");
            return false;
        }
        return true;
    }

    @FXML
    private void triggerEnter(javafx.scene.input.KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)  {
            login(new ActionEvent());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
