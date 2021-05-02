package accountant.model;

import accountant.model.DAO.CashDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

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
}
