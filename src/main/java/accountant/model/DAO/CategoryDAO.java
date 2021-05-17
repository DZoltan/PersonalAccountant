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

/**
 * DAO class for {@link Category} entity.
 * */
@RegisterBeanMapper(Category.class)
public interface CategoryDAO {

    /**
     * Create a table for {@link Category} objects.
     * */
    @SqlUpdate("""
           CREATE TABLE IF NOT EXISTS Category (
            id INTEGER PRIMARY KEY,
            in_out BOOLEAN,
            category_name VARCHAR NOT NULL,
            profile_id INTEGER NOT NULL
            )
            """)
    void createCategoryTable();

    /**
     * Store a new Category in Database
     * @param category An object of {@link Category}
     * */
    @SqlUpdate("INSERT INTO Category VALUES (:id, :in_out, :category_name, :profile_id)")
    void insertNewCategory(@BindBean Category category);

    /**
     * Modify the stored Category.
     * @param category_name Name of the category.
     * @param id id of the category. Must be the same as stored.
     * @param in_out Boolean. If the category is income then true, otherwise false.
     * */
    @SqlUpdate("UPDATE Category SET category_name = :category_name, in_out = :in_out WHERE (id = :id)")
    void updateCategory(@Bind("id") int id, @Bind("category_name") String category_name, @Bind("in_out") boolean in_out);

    /**
     * Delete a category from Database, which contains the given id.
     * @param id Id of the category to be deleted.
     * */
    @SqlUpdate("DELETE FROM Category WHERE(id = :id)")
    void deleteCategory(@Bind("id") int id);

    /**
     * @param id Id of the Category
     * @return Return with the name of the Category,which belongs to given id.
     * */
    @SqlQuery("SELECT category_name FROM Category WHERE(id = :id)")
    Optional<String> searchCategorybyId(@Bind("id") int id);

    @SqlQuery("SELECT id FROM Category WHERE(category_name = :category_name AND profile_id = :profile_id)")
    Optional<String> searchCategoryIdbyName(@Bind("category_name") String category_name, @Bind("profile_id") int profile_id);

    @SqlQuery("SELECT * FROM Category WHERE(category_name = :category_name AND profile_id = :profile_id)")
    Optional<Category> searchCategorybyName(@Bind("category_name") String category_name, @Bind("profile_id") int profile_id);


    @SqlQuery("SELECT * FROM Category ORDER BY id")
    List<Category> listAllCategory();

    @SqlQuery("SELECT * FROM Category WHERE ( profile_id = :profile_id) ORDER BY id")
    List<Category> listProfileCategory(@Bind("profile_id") int profile_id);
}
