package accountant;

import accountant.model.CashHandler;
import accountant.model.Category;
import accountant.model.CategoryHandler;
import accountant.model.ProfileQuarry;
import javafx.application.Application;
import org.jdbi.v3.core.Jdbi;

public class Main {

    public static void main(String[] args) {
        Application.launch(AccountantStart.class, args);
    }
}
