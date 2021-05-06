package accountant.controller;

import accountant.model.ProfileQuarry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;

public class LoginController {

    ProfileQuarry profile = new ProfileQuarry();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void login(ActionEvent actionEvent) {
        if(profile.loginProfile(username.getText(), password.getText())){

        }
        else{
            Alert passwordAlert = new Alert(Alert.AlertType.WARNING);
            passwordAlert.setHeaderText("Hibás adat");
            passwordAlert.setContentText("A megadott felhasználónév/jelszó hibás");
            passwordAlert.showAndWait();
        }

    }
}
