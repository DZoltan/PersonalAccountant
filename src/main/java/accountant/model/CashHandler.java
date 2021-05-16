package accountant.model;

import accountant.model.DAO.CashDAO;
import javafx.scene.control.Alert;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

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

    public CashHandler(){
        try{
            dao.createCashTable();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void setNewTransaction(Cash cash){
        try{
            dao.insertCash(cash);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    public List<Cash> getAllTransaction(){
        return  dao.getAllTransactions();
    }

    public int setCashId(List<Cash> listCash){
        if(listCash.size() == 0) return 0;
        else {
            return  listCash.stream().mapToInt(Cash::getCashId).max().orElseThrow() + 1;
        }
    }

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

    public int ParseMoney(String summaryText, Category category){
         int money = Integer.parseInt(summaryText);

        if(category.isIn_out()){
            return 0 + Math.abs(money);
        }
        else{
            return 0-Math.abs(money);
        }
    }

    public void calculateBalance(int profile_id) {
        int balance = 0;
        for(int i = 0; i< getOwnTransaction(profile_id).size(); i++){
            balance += getOwnTransaction(profile_id).get(i).getMoney();
        }
        profileHandler.updateBalance(profile_id, balance);
    }

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
            System.out.println(e);
        }
        return 0;
    }

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
