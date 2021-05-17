package accountant.model;

import accountant.model.DAO.CashDAO;
import javafx.scene.control.Alert;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;


/**
 * Class representing the connection between the database and {@link Cash}
 * */
public class CashHandler {

    Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
            .installPlugin(new SqlObjectPlugin());

    Handle handle = jdbi.open();
    CashDAO dao = handle.attach(CashDAO.class);

    ProfileHandler profileHandler = new ProfileHandler();
    CategoryHandler categoryHandler = new CategoryHandler();
    /**
     * Constructor of class. Create a table in DB, if not exists.
     * */
    public CashHandler(){
        try{
            dao.createCashTable();
        }
        catch (Exception e){
            Logger.error("Error creating table" + e);
        }
    }
    /**
     * Store a new Transaction in Database.
     * @param cash Object of {@link Cash} includes the data of transaction.
     * */
    public void setNewTransaction(Cash cash){
        try{
            dao.insertCash(cash.getCashId(), cash.getProfile_id(), cash.getMoney(), cash.getCategory_id(), cash.getDescription());
        }
        catch(Exception e){
            Logger.error("The application can't store this value in DataBase" + e);
        }
    }

    /**
     * Calls a method from DAO to return the list of Cash .
     * @return Return the list of  Cash.
     * */
    public List<Cash> getAllTransaction(){
        return  dao.getAllTransactions();
    }

    /**
     * Give an ID to new {@link Cash} objects
     * @param listCash List of Cash
     * @return if the list not empty max+ 1, otherwise 0
     * */
    public int setCashId(List<Cash> listCash){
        if(listCash.size() == 0) return 0;
        else {
            return  listCash.stream().mapToInt(Cash::getCashId).max().orElseThrow() + 1;
        }
    }
    /**
     * Calls a method from DAO. The list will contain all {@link Cash} objects of user.
     * @param profile_id Id of user.
     * @return List of user's Cash.
     * */
    public List<Cash> getOwnTransaction(int profile_id){
        return dao.getOwnTransactions(profile_id);
    }

    public List<Cash> getInOutTransaction(boolean in_out){
        if(in_out){
            return dao.getInTransactions();
        }
        else{
            return dao.getOutTransactions();
        }
    }
    /**
     * Parse a String to int, if the category is expense, number is negativev, otherwise positive.
     * @param category category of {@link Cash}
     * @param summaryText Given number in String from GUI.
     * @return If the Category is expense : -{@param summaryText}, otherwise {@param summaryText}
     * @exception NumberFormatException if the {@param summaryText} is NaN, throw this exception
     * */
    public int ParseMoney(String summaryText, Category category){
         int money = Integer.parseInt(summaryText);

        if(category.isIn_out()){
            return 0 + Math.abs(money);
        }
        else{
            return 0-Math.abs(money);
        }
    }
    /**
     * Calculate the actual balance from the dates, which stored in Database, then store to profile.
     * @param profile_id The id of user.
     * */
    public void calculateBalance(int profile_id) {
        int balance = 0;
        for(int i = 0; i< getOwnTransaction(profile_id).size(); i++){
            balance += getOwnTransaction(profile_id).get(i).getMoney();
        }
        profileHandler.updateBalance(profile_id, balance);
    }
    /**
     * Calculate the Summary of the expense in the past 7 days.
     * This method ignores the incomes.
     * @param total List of total cash of user.
     * @return summary of expense in the past 7 days.
     * */
    public int calculateWeeklyTotal(List<Cash> total)  {

        try {
            int weekly_total = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            for(Cash value : total){
                LocalDate localDate = LocalDate.parse(value.getDate(),formatter);
                if(localDate.isAfter(today.minusWeeks(1)) && value.getMoney() < 0){
                    weekly_total += value.getMoney();
                }
            }

            return abs(weekly_total);

        }
        catch (DateTimeParseException e){
            Logger.error("Invalid Date " + e);
        }
        return 0;
    }
    /**
     * Calculate which was the most common category of the past 7 Days.
     * The most common category is not equal with the highest expenditure.
     * @param total List of user's Transaction
     * @return Return whit the most common Category , if the list is empty or cannot be decided, this value is 0.
     * */
    public int calculateWeeklyBest(List<Cash> total){

        if(total.size() == 0){
            return -1;
        }

        List<Cash> weekly = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        for (Cash value : total){
            LocalDate localDate = LocalDate.parse(value.getDate(),formatter);
            if(localDate.isAfter(today.minusWeeks(1)) && value.getMoney() < 0){
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

        if(count == 1){
            return -1;
        }

        int category_id = counted
                .entrySet()
                .stream()
                .filter(integerLongEntry -> integerLongEntry.getValue().intValue() == count)
                .mapToInt(integerLongEntry -> integerLongEntry.getKey())
                .findFirst()
                .orElseThrow();

        return category_id;

    }
    /**
     * Calculate the summary of the expense, which belongs to most common category.
     * @param profile_id The id of the user.
     * @param categoryName name of most common category -> {@code calculateWeeklyBest}
     * @return Summary of the most common category's expense.
     * */
    public int calculateWeeklyTotalOfBest(int profile_id, String categoryName){

        List<Cash> total = getOwnTransaction(profile_id);
        int bestCategoryId = categoryHandler.selectCategoryIdByName(categoryHandler.selectCategorybyId(calculateWeeklyBest(total)), profile_id);
        int weeklyTotalOfBest = 0;

        for(Cash value: total){
            if(value.getCategory_id() == bestCategoryId){
                weeklyTotalOfBest += value.getMoney();
            }
        }

        return abs(weeklyTotalOfBest);

    }

}
