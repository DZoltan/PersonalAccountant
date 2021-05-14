package accountant.controller;

import accountant.model.Cash;
import org.junit.jupiter.api.Test;

import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {

    MenuController menuController = new MenuController();

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
        cash2.add(new Cash(6, 1, 2000, 4,"2021-05-18", "test"));
        cash2.add(new Cash(7, 1, -15000, 4,"2021-05-09", "test"));

    }


    @Test
    void calculateWeeklyTotal() {
        fillList();
        assertEquals(0, menuController.calculateWeeklyTotal(cash));
        assertEquals(170000, menuController.calculateWeeklyTotal(cash1));
        assertEquals(122000, menuController.calculateWeeklyTotal(cash2));

    }

    @Test
    void calculateWeeklyBest() {
        fillList();
        assertEquals(-1, menuController.calculateWeeklyBest(cash));
        assertEquals(6, menuController.calculateWeeklyBest(cash1));
        assertEquals(-1, menuController.calculateWeeklyBest(cash2));
    }
}