package accountant.model;

public class CategoryHandler {

    public void setNewCategory(boolean inOut, String name){
        int id = 0; // +1 az utolsóhoz
        Category newCategory = new Category(id);
    }

    public void deleteCategory(int id){

    }

    public void updateCategory(int id, boolean inOut, String name){

        Category categoryToUpdate = selectCategory(id);
        categoryToUpdate.setCategoryName(name);
        categoryToUpdate.setInOut(inOut);

    }

    public Category selectCategory(int id){

        Category selectedCategory = new Category(id);
        selectedCategory.setCategoryName("dummy"); //test
        selectedCategory.setInOut(true); //test


        return selectedCategory;
    }
}
