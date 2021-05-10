package accountant.model.DAO;

import accountant.model.Cash;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Date;
import java.util.List;

@RegisterBeanMapper(Cash.class)
public interface CashDAO {

    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS Cash(
                cash_id INTEGER PRIMARY KEY,
                profile_id INTEGER,
                money INTEGER,
                category_id INTEGER,
                description VARCHAR,
                date DATE
            )
            """)
    void createCashTable();

    /*@SqlUpdate("INSERT INTO Cash VALUES (:cash_id, :profile_id :money, :category_id, 'alma')")
    void insertCash(@BindBean Cash cash);*/

    @SqlUpdate("INSERT INTO Cash VALUES (:cash_id, :profile_id, :money, :category_id, :description, date(\"now\"))")
    void insertCash(@Bind("cash_id") int cash_id,@Bind("profile_id") int profile_id,@Bind("money") int money, @Bind("category_id") int category_id, @Bind("description") String description);

    @SqlQuery("SELECT * FROM Cash ORDER BY date")
    List<Cash> getAllTransactions();

    @SqlQuery("SELECT * FROM Cash WHERE profile_id = :profile_id ORDER BY date")
    List<Cash> getOwnTransactions(@Bind("profile_id") int profile_id);

    @SqlQuery("SELECT * FROM Cash WHERE(money > 0) ORDER BY date")
    List<Cash> getInTransactions();

    @SqlQuery("SELECT * FROM Cash WHERE(money < 0) ORDER BY date")
    List<Cash> getOutTransactions();
}
