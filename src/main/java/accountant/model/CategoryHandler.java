package accountant.model;

import accountant.model.DAO.CategoryDAO;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.sqlite.SQLiteException;
import org.tinylog.Logger;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

/**
 * Class representing the connection between the {@link Category} and Database.
 * */
public class CategoryHandler {

    Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
            .installPlugin(new SqlObjectPlugin());

    Handle handle = jdbi.open();
    CategoryDAO dao = handle.attach(CategoryDAO.class);

    /**
     * Constructor of class. Create a table for {@link Category} in database.
     * */
    public CategoryHandler(){
        try{
            dao.createCategoryTable();
        }
        catch(Exception e){
            Logger.error("error in Database : " + e);
        }
    }

    /**
     * Create an object of Category class, and store it in Database.
     * @param inOut Category is expense or income.
     * @param name The name of the Category.
     * @param profile_id The user, who stored the category
     * */
    public void setNewCategory(boolean inOut, String name, int profile_id){
            int id = getNewCategoryId(dao.listAllCategory());
            dao.insertNewCategory(new Category(id, inOut, name,profile_id));
    }
    /**
     * If the user want to store a category, this method give an id.
     * @param categoryList list of all category.
     * @return if the list is not empty, max +1, otherwise 0.
     * */
    public int getNewCategoryId(List<Category> categoryList){
        if(categoryList.size() == 0) return 0;
        else {
            return  categoryList.stream().mapToInt(Category::getId).max().orElseThrow() + 1;
        }
    }
    /**
     * Update the category name, or/and the type (income/expense).
     * @param id the given id of category
     * @param category_name The name of category
     * @param in_out True: income, False: expense
     * */
    public void updateCategory(int id, String category_name, boolean in_out){
        dao.updateCategory(id, category_name, in_out);
    }
    /**
     * Delete a category from Database.
     * @param id The given id of Category
     * */
    public void deleteCategory(int id){
        dao.deleteCategory(id);
    }
    /**
     * List the categories, which belong the user.
     * @param profile_id The id of the user.
     * @return A list that contains the all categories of the user.
     * */
    public List<Category> selectOwnCategory(int profile_id){
        return  dao.listProfileCategory(profile_id);
    }

    /**
     * Return the name of the selected category
     * @param id The id of a category
     * @return the name of the category that belongs the id, otherwise the return value is "not found".
     * @exception NoSuchElementException If the table doesn't contain the id.
     * */
    public String selectCategorybyId(int id){
        try {
            return dao.searchCategorybyId(id).orElseThrow();
        }
        catch (NoSuchElementException e){
            Logger.error("The database doesn't contain this id: " + id);
            return "Not found";
        }
    }
    /**
     * Return the id of the selected category.
     * @param name The name of category.
     * @param profile_id User, who created the category.
     * @return Id of the category, which belongs to user, otherwise the return value is -1.
     * @exception NoSuchElementException If the table doesn't contain the category, or belongs to other user.
     * */
    public int selectCategoryIdByName(String name, int profile_id){
        try {
            return Integer.parseInt(dao.searchCategoryIdbyName(name, profile_id).orElseThrow());
        }
        catch (NoSuchElementException e) {
            Logger.error("The database doesn't contain this name: " + name +" or belongs to other user.");
            return -1;
        }
    }
    /**
     * @param name The name of category.
     * @param profile_id User, who created the category.
     * @return An object of {@link Category}, otherwise the return value is an empty object.
     * */
    public Category selectCategorybyName(String name, int profile_id){
        try {
            return dao.searchCategorybyName(name, profile_id).orElseThrow();
        }
        catch (NoSuchElementException e){
            Logger.error("The database doesn't contain this name: " + name +" or belongs to other user.");
            return new Category();
        }
    }


}
