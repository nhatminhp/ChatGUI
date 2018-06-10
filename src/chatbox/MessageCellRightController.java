package chatbox;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import message.Message;

public class MessageCellRightController {
    @FXML
    private ImageView imageView;
    @FXML private Label messageLabel;

    public void setMessageCell(Message m)
    {
        Image i = new Image(getClass().getResourceAsStream("../images/profile.png"));
        imageView.setImage(i);
        messageLabel.setText(m.getMsg());
    }
}
