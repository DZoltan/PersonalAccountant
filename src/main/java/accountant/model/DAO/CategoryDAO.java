package accountant.model.DAO;

import accountant.model.Category;
import javafx.collections.ObservableList;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.MapTo;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
@RegisterBeanMapper(Category.class)
public interface CategoryDAO {

    @SqlUpdate("""
           CREATE TABLE IF NOT EXISTS Category (
            id INTEGER PRIMARY KEY,
            in_out BOOLEAN,
            category_name VARCHAR NOT NULL,
            profile_id INTEGER NOT NULL
            )
            """)
    void createCategoryTable();

    @SqlUpdate("INSERT INTO Category VALUES (:id, :in_out, :category_name, :profile_id)")
    void insertNewCategory(@BindBean Category category);

    @SqlUpdate("UPDATE Category SET category_name = :category_name, in_out = :in_out WHERE (id = :id)")
    void updateCategory(@Bind("id") int id, @Bind("category_name") String category_name, @Bind("in_out") boolean in_out);

    @SqlUpdate("DELETE FROM Category WHERE(id = :id)")
    void deleteCategory(@Bind("id") int id);

    @SqlUpdate("DELETE FROM Category")
    void deleteAllCategory();

    @SqlQuery("SELECT category_name FROM Category WHERE(category_name = :category_name)")
    Optional<String> searchCategoryName(@Bind("category_name") String category_name);

    @SqlQuery("SELECT category_name FROM Category WHERE(id = :id)")
    Optional<String> searchCategorybyId(@Bind("id") int id);

    @SqlQuery("SELECT id FROM Category WHERE(category_name = :category_name AND profile_id = :profile_id)")
    Optional<String> searchCategoryIdbyName(@Bind("category_name") String category_name, @Bind("profile_id") int profile_id);


    @SqlQuery("SELECT * FROM Category ORDER BY id")
    List<Category> listAllCategory();

    @SqlQuery("SELECT * FROM Category WHERE ( profile_id = :profile_id) ORDER BY id")
    List<Category> listProfileCategory(@Bind("profile_id") int profile_id);
}
