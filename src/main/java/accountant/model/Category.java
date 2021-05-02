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
    @ColumnName("id")
    public int id;
    @ColumnName("in_out")
    boolean in_out;
    @ColumnName("category_name")
    String category_name;
    @ColumnName("profile_id")
    int profile_id;

    }
