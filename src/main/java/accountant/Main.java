package accountant;

import accountant.model.ProfileQuarry;
import org.jdbi.v3.core.Jdbi;

public class Main {

    public static void main(String[] args) {

        ProfileQuarry test = new ProfileQuarry();
        test.loginProfile("Bela", "alma");
        test.loginProfile("Bela", "körte");
        test.loginProfile("Álmos", "alma");
        //test.test_method();
    }
}
