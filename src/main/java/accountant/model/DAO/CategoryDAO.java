package accountant.model.DAO;

import accountant.model.Category;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
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
     * Store a new {@link Category} in Database
     * @param category An object of {@link Category}
     * */
    @SqlUpdate("INSERT INTO Category VALUES (:id, :in_out, :category_name, :profile_id)")
    void insertNewCategory(@BindBean Category category);

    /**
     * Modify the stored {@link Category}.
     * @param category_name Name of the {@link Category}.
     * @param id id of the {@link Category}. Must be the same as stored.
     * @param in_out Boolean. If the category is income then true, otherwise false.
     * */
    @SqlUpdate("UPDATE Category SET category_name = :category_name, in_out = :in_out WHERE (id = :id)")
    void updateCategory(@Bind("id") int id, @Bind("category_name") String category_name, @Bind("in_out") boolean in_out);

    /**
     * Delete a category from Database, which contains the entered id.
     * @param id Id of the category to be deleted.
     * */
    @SqlUpdate("DELETE FROM Category WHERE(id = :id)")
    void deleteCategory(@Bind("id") int id);

    /**
     * @param id Id of the {@link Category}{@link Category}
     * @return Return with the name of the {@link Category},which belongs to entered id.
     * */
    @SqlQuery("SELECT category_name FROM Category WHERE(id = :id)")
    Optional<String> searchCategorybyId(@Bind("id") int id);

    /**
     * Select a category ID, which matches the entered category.
     * @param category_name Name of the {@link Category}
     * @param profile_id User to whom the {@link Category} belongs.
     * @return Return with the id of the {@link Category}.
     * */
    @SqlQuery("SELECT id FROM Category WHERE(category_name = :category_name AND profile_id = :profile_id)")
    Optional<String> searchCategoryIdbyName(@Bind("category_name") String category_name, @Bind("profile_id") int profile_id);

    /**
     * Select a category, which matches the entered category.
     * @param category_name Name of the {@link Category}
     * @param profile_id User to whom the {@link Category} belongs.
     * @return Return with the entity of the {@link Category}.
     * */
    @SqlQuery("SELECT * FROM Category WHERE(category_name = :category_name AND profile_id = :profile_id)")
    Optional<Category> searchCategorybyName(@Bind("category_name") String category_name, @Bind("profile_id") int profile_id);


    /**
     * List all {@link Category}. Ordered by id.
     * @return List of {@link Category}.
     * */
    @SqlQuery("SELECT * FROM Category ORDER BY id")
    List<Category> listAllCategory();

    /**
     * List all {@link Category}, which belongs to User's ID. Ordered by id.
     * @return List of {@link Category}, which belongs to User's ID.
     * */
    @SqlQuery("SELECT * FROM Category WHERE ( profile_id = :profile_id) ORDER BY id")
    List<Category> listProfileCategory(@Bind("profile_id") int profile_id);
}
