package chatbox;

import application.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SearchFriendsController implements Initializable {

    @FXML private TextField searchTextField;
    @FXML private ListView<Friend> friendListView;

    private FilteredList<Friend> filteredData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Friend> listFriends = FriendList.getContacts();
        for(Friend f : listFriends)
        {
            System.out.println("F " + f.getUsername());
        }

        filteredData = new FilteredList<>(listFriends, p -> true);

        ObjectProperty<Predicate<Friend>> nameFilter = new SimpleObjectProperty<>();

        nameFilter.bind(Bindings.createObjectBinding(() ->
                f -> {
                    String text = searchTextField.getText();
                    if(text == null || text.equals("")) return true;
                    if(f.getUsername().toLowerCase().contains(text.toLowerCase()))
                        return true;
                    else return false;
                }, searchTextField.textProperty()));

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> nameFilter.get(),
                nameFilter));

        //SortedList<Friend> sortedData = new SortedList<>(filteredData);
        //sortedData.comparatorProperty().bind(friendListView.comparatorProperty());


        friendListView.setCellFactory(new Callback<ListView<Friend>, ListCell<Friend>>(){

            @Override
            public ListCell<Friend> call(ListView<Friend> p) {

                ListCell<Friend> cell = new ListCell<Friend>(){
                    @Override
                    protected void updateItem(Friend t, boolean bln) {
                        Helper helper = new Helper();
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(helper.normalize(t.getUsername()));
                        }
                    }
                };

                return cell;
            }
        });

        friendListView.setItems(filteredData);
    }
}
