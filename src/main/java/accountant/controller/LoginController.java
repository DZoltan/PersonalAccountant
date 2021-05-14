package accountant.controller;

import accountant.model.ProfileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;


public class LoginController {

    ProfileHandler profileHandler = new ProfileHandler();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label registration_lbl;

    @FXML
    private Button login;

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        if(profileHandler.loginProfile(username.getText(), password.getText())){
            Logger.info(username.getText() + " Sikeresen bejelentkezett.");
            Alert succeddAlert = new Alert(Alert.AlertType.INFORMATION);
            succeddAlert.setHeaderText("Sikeres bejelentkezés");
            succeddAlert.showAndWait();

            switchToMenu(actionEvent);
        }
        else{
            Logger.error(username.getText() + " : Bejelentkezési hiba!");

            Alert passwordAlert = new Alert(Alert.AlertType.WARNING);
            passwordAlert.setHeaderText("Hibás adat");
            passwordAlert.setContentText("A megadott felhasználónév/jelszó hibás");
            passwordAlert.showAndWait();
        }

    }

    public void registration(ActionEvent actionEvent) throws IOException {
        if(!login.isDisable())
        {
            registration_lbl.setVisible(true);
            login.setDisable(true);
        }
        else if(login.isDisable())
        {
            profileHandler.CreateProfile(username.getText(), password.getText());

            Logger.info(username.getText() + " Sikeresen regisztrált.");

            Alert succeddAlert = new Alert(Alert.AlertType.INFORMATION);
            succeddAlert.setHeaderText("Sikeres regisztráció");
            succeddAlert.setContentText("A profil regisztrációja siekres volt.");
            succeddAlert.showAndWait();

            switchToMenu(actionEvent);
        }
    }

    public void switchToMenu(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/menu.fxml"));
        Parent root = fxmlLoader.load();
        MenuController menu = fxmlLoader.<MenuController>getController();
        menu.initalize(profileHandler.profile);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
