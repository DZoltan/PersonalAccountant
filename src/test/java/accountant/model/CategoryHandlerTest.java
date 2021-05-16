package accountant.model;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryHandlerTest {

    CategoryHandler categoryHandler = new CategoryHandler();
    @Test
    void getNewCategoryId() {
        List<Category> category = new ArrayList<>();
        List<Category> category1 = new ArrayList<>();
        List<Category> category2 = new ArrayList<>();

        category1.add(new Category(0, true, "test", 1));
        category1.add(new Category(1, true, "test", 1));
        category1.add(new Category(2, true, "test", 1));

        category2.add(new Category(0, true, "test", 1));
        category2.add(new Category(3, true, "test", 1));
        category2.add(new Category(6, true, "test", 1));
        category2.add(new Category(2, true, "test", 1));
        category2.add(new Category(1, true, "test", 1));

        assertEquals(0,categoryHandler.getNewCategoryId(category));
        assertEquals(3,categoryHandler.getNewCategoryId(category1));
        assertEquals(7,categoryHandler.getNewCategoryId(category2));


    }
}