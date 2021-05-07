package accountant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class MenuController {

    public String username;

    @FXML
    private TextArea welcome_txt;

    public void logout(ActionEvent event) throws IOException {
        Logger.info(username + " kijelentkezett.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxs/login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }
}
