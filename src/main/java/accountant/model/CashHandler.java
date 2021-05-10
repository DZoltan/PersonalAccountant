package accountant.model;

import accountant.model.DAO.CashDAO;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class CashHandler {

    Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
            .installPlugin(new SqlObjectPlugin());

    Handle handle = jdbi.open();
    CashDAO dao = handle.attach(CashDAO.class);


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
            dao.insertCash(cash.getCashId(), cash.getProfile_id(), cash.getMoney(), cash.getCategory_id(), cash.getDescription());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    public List<Cash> getAllTransaction(){
        return  dao.getAllTransactions();
    }

    public int setCashId(){
        int id = 0;
        try{
            id = dao.getAllTransactions().stream().mapToInt(Cash::getCashId).max().orElseThrow() + 1;
        }
        catch(NoSuchElementException e){

        }

        return id;
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




}
