package accountant;

import accountant.controller.CategoryController;
import accountant.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountantStart extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("PersonalAccountant");
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController login = fxmlLoader.<LoginController>getController();;
        login.init();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
