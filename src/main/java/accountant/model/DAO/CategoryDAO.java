package accountant.model.DAO;

import accountant.model.Category;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    @SqlUpdate("""
            CREATE TABLE Category (
            id INTEGER PRIMARY KEY,
            category_name VARCHAR NOT NULL,
            profile INTEGER NOT NULL,
            in_out BOOLEAN)
            """)
    void createCategoryTable();

    @SqlUpdate("INSERT INTO Category VALUES (:id, :category_name, :profile, :in_out)")
    void insertNewCategory(@Bind("id") int id, @Bind("category_name") String category_name, @Bind("profile") int profile_id, @Bind("in_out") boolean in_out);

    @SqlUpdate("UPDATE Category SET (category_name = :category_name, in_out = :in_out) WHERE (id = :id)")
    void updateCategory(@Bind("id") int id, @Bind("category_name") String category_name, @Bind("in_out") boolean in_out);

    @SqlQuery("SELECT category_name FROM Category WHERE(id = :id)")
    Optional<String> searchCategory(@Bind("id") int id);

    @SqlQuery("SELECT * FROM Category")
    List<Category> listAllCategory(@Bind("id") int id);
}
