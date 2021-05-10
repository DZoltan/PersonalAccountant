package accountant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cash {

    public int cashId;
    int profile_id;
    int money;
    int category_id;
    String date;
    String description;



}
