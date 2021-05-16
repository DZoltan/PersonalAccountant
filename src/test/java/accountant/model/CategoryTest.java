package accountant.model;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void getData() {
        Category category1 = new Category(0,true, "teszt", 1);
        Category category2 = new Category(1,false, "teszt", 1);

        assertEquals("ID: " + 0 + "\t\t" + "Income" + "\t\t" + "teszt", category1.getData());
        assertEquals("ID: " + 1 + "\t\t" + "Expense" + "\t\t" + "teszt", category2.getData());
    }
}