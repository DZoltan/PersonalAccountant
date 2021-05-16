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

    /**
     * Class representing the Category, which the user can classified the transactions.
     * */

public class Category {
    public int id;
    boolean in_out;
    String category_name;
    int profile_id;

    /**
     * This function return with a String, which contains the data of each Category
     * @return id, InOut Type and name of category
     * */
    public String getData(){
        String inOut = "";
        if(in_out) inOut="Income";
        else inOut="Expense";
       return "ID: " + this.id + "\t\t" + inOut + "\t\t" + category_name;
    }
    }
