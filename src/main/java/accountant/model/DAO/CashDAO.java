package accountant.model.DAO;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CashDAO {

    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS Cash(
                cash_id INTEGER PRIMARY KEY,
                money INTEGER,
                category_id INTEGER,
                date DATE,
                description VARCHAR
            )
            """)
    void createCashTable();
}
