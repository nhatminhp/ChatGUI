package chatbox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import message.Message;

import java.io.IOException;

public class MessageCell extends ListCell<Message> {


    private FXMLLoader fxmlLoader;

//    @FXML
    private HBox hbox;
//    @FXML
    private ImageView imageView;
//    @FXML
    private Label messageLabel;

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if(empty || message == null) {

            setText(null);
            setGraphic(null);

        } else {

//            if (fxmlLoader == null)
//            {
                if(message.getFromID() == ChatController.getCurrentUserID())
                {
                    fxmlLoader = new FXMLLoader(getClass().getResource("MessageCellRight.fxml"));
                    try {
                        hbox = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MessageCellRightController controller = fxmlLoader.getController();
                    //controller.setMessageCell(message);
                }
                else
                {
                    fxmlLoader = new FXMLLoader(getClass().getResource("MessageCellLeft.fxml"));
                    try {
                        hbox = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MessageCellLeftController controller = fxmlLoader.getController();
                   // controller.setMessageCell(message);
                }

//                try {
//                    fxmlLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                imageView = (ImageView) hbox.lookup("#imageView");
                messageLabel = (Label) hbox.lookup("#messageLabel");

                Image i = new Image(getClass().getResourceAsStream("../images/profile.png"));
                imageView.setImage(i);
                messageLabel.setText(message.getMsg());

                setText(null);
                setGraphic(hbox);

                System.out.println("hahahahaah");
//            }

//            setText(message.getMsg());
//
//            Image i = new Image(getClass().getResourceAsStream("../images/profile.png"));
//            ImageView ii = new ImageView(i);
//            ii.setFitWidth(50);
//            ii.setFitHeight(50);
//            setGraphic(ii);

        }

    }
}
