package ru.learnup.javaqa.api.asserts;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import ru.learnup.javaqa.api.dto.Product;

import java.util.List;

import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

public class IsCategoryFood extends TypeSafeMatcher<List<Product>> {

    public static Matcher<List<Product>> isCategoryFood() {
        return new IsCategoryFood();
    }

    @Override
    protected boolean matchesSafely(List<Product> products) {
        for(Product product : products) {
            if (!product.getCategoryTitle().equals(FOOD.getName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No all products have category 'Food'");
    }
}
