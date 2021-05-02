package accountant.model;

import lombok.Data;

import java.util.Date;

@Data
public class Cash {

    public int cashId;
    int money;
    int category_id;
    Date date;
    String description;

}
