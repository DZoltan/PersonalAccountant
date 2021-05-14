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

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        welcome_txt.setText("Üdvözlünk, " + profile.getUsername() + "\t\t" + "Egyenleg : " + profile.getBalance());
        int WeeklyTotal = calculateWeeklyTotal(cashHandler.getOwnTransaction(profile.getId()));
        weekly_total.setText("Heti kiadás: " + WeeklyTotal);
        weekly_best.setText("A leggyakoribb kateógira a héten: " + calculateWeeklyBest(cashHandler.getOwnTransaction(profile.getId())));
        weekly_total_of_best.setText("A leggyakoribb kategória kiadása a héten : " + calculateWeeklyTotalOfBest(calculateWeeklyBest(cashHandler.getOwnTransaction(profile.getId()))));

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

    public int calculateWeeklyTotal(List<Cash> total)  {

        try {
            int weekly_total = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for(Cash value : total){
                LocalDate localDate = LocalDate.parse(value.getDate(),formatter);
                if(localDate.isAfter(localDate.minusWeeks(1)) && value.getMoney() < 0){
                    weekly_total += value.getMoney();
                }
            }

            return abs(weekly_total);

        }
        catch (DateTimeParseException e){
            System.out.println(e);
        }
        return 0;
    }

    public String calculateWeeklyBest(List<Cash> total){
        List<Cash> weekly = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Cash value : total){
            LocalDate localDate = LocalDate.parse(value.getDate(),formatter);
            if(localDate.isAfter(localDate.minusWeeks(1)) && value.getMoney() < 0){
                weekly.add(value);
            }
        }
        Map<Integer, Long> counted = weekly
                .stream()
                .collect(Collectors.groupingBy(Cash::getCategory_id, Collectors.counting()));

        int count = counted
                .entrySet()
                .stream()
                .mapToInt(value -> value.getValue().intValue())
                .max()
                .orElseThrow();

        int category_id = counted
                .entrySet()
                .stream()
                .filter(integerLongEntry -> integerLongEntry.getValue().intValue() == count)
                .mapToInt(integerLongEntry -> integerLongEntry.getKey())
                .findFirst()
                .orElseThrow();

        return categoryHandler.selectCategorybyId(category_id);

    }

    public int calculateWeeklyTotalOfBest(String categoryName){

        List<Cash> total = cashHandler.getOwnTransaction(profile.getId());
        int bestCategoryId = categoryHandler.selectCategoryIdbyName(calculateWeeklyBest(total), profile.getId());
        int weeklyTotalOfBest = 0;

        for(Cash value: total){
            if(value.getCategory_id() == bestCategoryId){
                weeklyTotalOfBest += value.getMoney();
            }
        }

        return abs(weeklyTotalOfBest);

    }
}
