package accountant.model;

import accountant.model.DAO.CashDAO;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

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
            dao.insertCash(cash);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Cash> getAllTransaction(){
        return  dao.getAllTransactions();
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
