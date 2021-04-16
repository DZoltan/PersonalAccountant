package accountant.model;

import lombok.Data;

@Data
public class Categories {

    String CategoryName;
    boolean InOut;
    public int id;

    public Categories(String categoryName, boolean inOut, int id) {
        CategoryName = categoryName;
        InOut = inOut;
        this.id = id;
    }

    public Categories(int id){
        this.id = id;
    }
}
