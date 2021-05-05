package accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.MapTo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    public int id;
    boolean in_out;
    String category_name;
    int profile_id;

    }
