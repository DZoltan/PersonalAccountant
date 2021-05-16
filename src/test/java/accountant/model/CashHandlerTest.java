package accountant.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashHandlerTest {
    CashHandler cashHandler = new CashHandler();


    List<Cash> cash = new ArrayList<>();
    List<Cash> cash1 = new ArrayList<>();
    List<Cash> cash2 = new ArrayList<>();

    public void fillList(){
        cash1.add(new Cash(0, 1, -30000, 7,"2021-05-14", "test"));
        cash1.add(new Cash(1, 1, -70000, 6,"2021-05-14", "test"));
        cash1.add(new Cash(2, 1, -70000, 6,"2021-05-14", "test"));
        cash1.add(new Cash(3, 1, -40000, 7,"2021-05-04", "test"));
        cash1.add(new Cash(4, 1, 300000, 4,"2021-05-14", "test"));

        cash2.add(new Cash(0, 1, -30000, 7,"2021-05-12", "test"));
        cash2.add(new Cash(1, 1, -70000, 9,"2021-05-13", "test"));
        cash2.add(new Cash(2, 1, -7000, 6,"2021-05-14", "test"));
        cash2.add(new Cash(3, 1, -40000, 7,"2021-05-04", "test"));
        cash2.add(new Cash(4, 1, 300000, 4,"2021-04-14", "test"));
        cash2.add(new Cash(5, 1, -1000, 4,"2021-04-19", "test"));
        cash2.add(new Cash(7, 1, -15000, 4,"2021-05-09", "test"));

    }

    @Test
    void parseMoney() {
        Category category1 = new Category(0, true, "teszt", 1);
        Category category2 = new Category(1, false, "teszt", 1);

        assertEquals(12000, cashHandler.ParseMoney("12000",category1));
        assertEquals(-12000, cashHandler.ParseMoney("12000",category2));
        assertEquals(12000, cashHandler.ParseMoney("-12000",category1));
        assertEquals(-12000, cashHandler.ParseMoney("-12000",category2));
    }

    @Test
    void calculateWeeklyTotal() {
        fillList();
        assertEquals(0, cashHandler.calculateWeeklyTotal(cash));
        assertEquals(170000, cashHandler.calculateWeeklyTotal(cash1));
        assertEquals(107000, cashHandler.calculateWeeklyTotal(cash2));

    }

    @Test
    void calculateWeeklyBest() {
        fillList();
        assertEquals(-1, cashHandler.calculateWeeklyBest(cash));
        assertEquals(6, cashHandler.calculateWeeklyBest(cash1));
        assertEquals(-1, cashHandler.calculateWeeklyBest(cash2));
    }

    @Test
    void setCashId() {
        fillList();
        assertEquals(0, cashHandler.setCashId(cash));
        assertEquals(5, cashHandler.setCashId(cash1));
        assertEquals(8, cashHandler.setCashId(cash2));
    }
}