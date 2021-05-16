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
            Logger.info(username.getText() + ": Login was successful.");
            Alert succeedAlert = new Alert(Alert.AlertType.INFORMATION);
            succeedAlert.setHeaderText("Login was successful.");
            succeedAlert.showAndWait();

            switchToMenu(actionEvent);
        }
        else{
            Logger.error(username.getText() + ": Failed to login ");

            Alert passwordAlert = new Alert(Alert.AlertType.WARNING);
            passwordAlert.setHeaderText("Bad Credentials");
            passwordAlert.setContentText("Invalid Username and/or Password");
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

            Logger.info(username.getText() + ": Registration was successful.");

            Alert succeedAlert = new Alert(Alert.AlertType.INFORMATION);
            succeedAlert.setHeaderText("Registration was successful.");
            succeedAlert.showAndWait();

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
