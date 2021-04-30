package accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Categories {

    String CategoryName;
    boolean InOut;
    public int id;

    public Categories(int id){
        this.id = id;
    }
}
