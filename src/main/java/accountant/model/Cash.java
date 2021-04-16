package accountant.model;

import lombok.Data;

import java.util.Date;

@Data
public class Cash {

    public int cashId;
    int amount;
    boolean inOut;
    int category;
    Date date;
    String description;

}
