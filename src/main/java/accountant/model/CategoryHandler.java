package accountant.model;

import accountant.model.DAO.CategoryDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

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

    public void setNewCategory(boolean inOut, String name){
        try {
            int id = dao.listAllCategory().size();
            dao.insertNewCategory(id, name, 1,inOut);
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

    public void testCategory(){
        try {
            dao.listAllCategory().forEach(System.out::println);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


}
