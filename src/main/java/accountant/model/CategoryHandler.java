package accountant.model;

public class CategoryHandler {

    public void setNewCategory(boolean inOut, String name){
        int id = 0; // +1 az utolsóhoz
        Categories newCategory = new Categories(name, inOut, id);
    }

    public void deleteCategory(int id){

    }

    public void updateCategory(int id, boolean inOut, String name){

        Categories categoryToUpdate = selectCategory(id);
        categoryToUpdate.setCategoryName(name);
        categoryToUpdate.setInOut(inOut);

    }

    public Categories selectCategory(int id){

        Categories selectedCategory = new Categories(id);
        selectedCategory.setCategoryName("dummy"); //test
        selectedCategory.setInOut(true); //test


        return selectedCategory;
    }
}
