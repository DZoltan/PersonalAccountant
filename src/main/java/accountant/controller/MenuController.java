package accountant.controller;

import accountant.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class MenuController {

    ProfileHandler profileHandler = new ProfileHandler();
    CashHandler cashHandler = new CashHandler();
    CategoryHandler  categoryHandler = new CategoryHandler();
    public Profile profile;


    @FXML
    private Text welcome_txt;

    @FXML
    private Text weekly_total;

    @FXML
    private Text weekly_best;

    @FXML
    private Text weekly_total_of_best;

    public void initalize(Profile profile){
        this.profile = profile;
        welcome_txt.setText("Welcome, " + profile.getUsername() + "!\t\t" + "Balance : " + profile.getBalance());
        int WeeklyTotal = cashHandler.calculateWeeklyTotal(cashHandler.getOwnTransaction(profile.getId()));
        weekly_total.setText("Weekly expense: " + WeeklyTotal);

        int bestCategory = cashHandler.calculateWeeklyBest(cashHandler.getOwnTransaction(profile.getId()));
        if(bestCategory != -1) {


            weekly_best.setText("Category of the week: " + categoryHandler.selectCategorybyId(bestCategory));
            weekly_total_of_best.setText("Cost of the Category of the Week : " + cashHandler.calculateWeeklyTotalOfBest(
                    profile.getId(),
                    categoryHandler.selectCategorybyId(
                            cashHandler.calculateWeeklyBest(
                                    cashHandler.getOwnTransaction(profile.getId())))));
        }
        else {
            weekly_best.setText("Category of the week: -");
            weekly_total_of_best.setText("Cost of the Category of the Week : -");
        }
    }

    public void logout(ActionEvent event) throws IOException {
        Logger.info(profile.getUsername() + " logged out.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxs/login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }


    public void category(ActionEvent event) throws IOException, InterruptedException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/category.fxml"));
        Parent root = fxmlLoader.load();
        CategoryController category = fxmlLoader.<CategoryController>getController();;
        category.setOwnCategory(profile.getId());
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void transaction(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxs/transaction.fxml"));
        Parent root = fxmlLoader.load();
        TransactionController transactionController = fxmlLoader.<TransactionController>getController();;
        transactionController.initalize(profile.getId());
        stage.setScene(new Scene(root));
        stage.show();
    }

}
