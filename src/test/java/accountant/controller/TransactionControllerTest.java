package accountant.controller;

import accountant.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {
    TransactionController transactionController = new TransactionController();
    @Test
    void parseMoney() {

        Category category1 = new Category(0, true, "teszt", 1);
        Category category2 = new Category(1, false, "teszt", 1);

        assertEquals(12000, transactionController.ParseMoney("12000",category1));
        assertEquals(-12000, transactionController.ParseMoney("12000",category2));
        assertEquals(12000, transactionController.ParseMoney("-12000",category1));
        assertEquals(-12000, transactionController.ParseMoney("-12000",category2));

    }
}