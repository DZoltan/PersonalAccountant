package accountant.model;

import accountant.model.DAO.CategoryDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.List;

public class CategoryHandler {

    Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
            .installPlugin(new SqlObjectPlugin());

    Handle handle = jdbi.open();
    CategoryDAO dao = handle.attach(CategoryDAO.class);
    public CategoryHandler(){
        try{
            dao.createCategoryTable();
        }
        catch(Exception e){
            System.out.println("Hiba az adatbázisban" + e);
        }
    }

    public void setNewCategory(boolean inOut, String name, int profile_id){

        try {
            int id = dao.listAllCategory().stream().mapToInt(Category::getId).max().orElseThrow() + 1;
            dao.insertNewCategory(new Category(id, inOut, name,profile_id));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateCategory(int id, String category_name, boolean in_out){
        dao.updateCategory(id, category_name, in_out);
    }

    public void deleteAll(){
        dao.deleteAllCategory();
    }

    public void deleteCategory(int id){
        dao.deleteCategory(id);
    }

    public void testCategory(){
        try {
            dao.listAllCategory().forEach(System.out::println);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public List<Category> selectAll(){
        return dao.listAllCategory();
    }

    public List<Category> selectOwnCategory(int id){
        return  dao.listProfileCategory(id);
    }

    public String selectCategorybyId(int id){
        return dao.searchCategorybyId(id).orElseThrow();
    }

    public int selectCategoryIdbyName(String name, int profile_id){
        return Integer.parseInt(dao.searchCategoryIdbyName(name, profile_id).orElseThrow());
    }


}
