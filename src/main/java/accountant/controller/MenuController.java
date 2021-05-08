package accountant.controller;

import accountant.model.Category;
import accountant.model.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    public Profile profile;


    @FXML
    private Text welcome_txt;

    public void setWelcome_txt(){
        welcome_txt.setText("Üdvözlünk, " + profile.getUsername());

    }

    public void logout(ActionEvent event) throws IOException {
        Logger.info(profile.getUsername() + " kijelentkezett.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxs/login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }


    public void category(ActionEvent event) throws IOException, InterruptedException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/category.fxml"));
        Parent root = fxmlLoader.load();
        CategoryController category = fxmlLoader.<CategoryController>getController();
        //System.out.println(profile.getId());
        category.setOwnCategory(profile.getId());
        stage.setScene(new Scene(root));
        stage.show();
    }


}
