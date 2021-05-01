package accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    String CategoryName;
    boolean InOut;
    public int id;
    int profile_id;

    public Category(int id){
        this.id = id;
    }
}
