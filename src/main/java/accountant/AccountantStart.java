package accountant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountantStart extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxs/login.fxml"));
        stage.setTitle("Personal Accountant");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
