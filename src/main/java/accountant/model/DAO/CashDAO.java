package accountant.model.DAO;

import accountant.model.Cash;
import accountant.model.Category;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Date;
import java.util.List;

/**
 * DAO class for {@link Cash} entity.
 * */
@RegisterBeanMapper(Cash.class)
public interface CashDAO {

    /**
     * Create a table for {@link Cash} objects.
     * */
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

    /**
     * Store a new {@link Cash} in Database.
     * */
    @SqlUpdate("INSERT INTO Cash VALUES (:cash_id, :profile_id, :money, :category_id, :description, date(\"now\"))")
    void insertCash(@Bind("cash_id") int cash_id, @Bind("profile_id") int profile_id, @Bind("money") int money, @Bind("category_id") int category_id, @Bind("description") String description);

    /**
     * List all {@link Cash} , ordered by date.
     * @return List of {@link Cash}.
     * */
    @SqlQuery("SELECT * FROM Cash ORDER BY date")
    List<Cash> getAllTransactions();

    /**
     * List all {@link Cash}, which belongs to user. Ordered by date.
     * @return List of {@link Cash}.
     * */
    @SqlQuery("SELECT * FROM Cash WHERE profile_id = :profile_id ORDER BY date")
    List<Cash> getOwnTransactions(@Bind("profile_id") int profile_id);

    /**
     * List all {@link Cash} incomes, ordered by date.
     * @return List of {@link Cash}.
     * */
    @SqlQuery("SELECT * FROM Cash WHERE(money > 0) ORDER BY date")
    List<Cash> getInTransactions();

    /**
     * List all {@link Cash} expenses, ordered by date.
     * @return List of {@link Cash}.
     * */
    @SqlQuery("SELECT * FROM Cash WHERE(money < 0) ORDER BY date")
    List<Cash> getOutTransactions();
}
