package accountant.model;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Data
@Builder
public class Cash {

    public int cashId;
    int money;
    int category_id;
    LocalDate date;
    String description;


}
