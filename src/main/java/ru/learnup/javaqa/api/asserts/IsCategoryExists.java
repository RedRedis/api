package ru.learnup.javaqa.api.asserts;

import lombok.NoArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import ru.learnup.javaqa.api.dto.Product;
import ru.learnup.javaqa.api.enums.CategoryType;

import java.util.List;

@NoArgsConstructor
public class IsCategoryExists extends TypeSafeMatcher<List<Product>> {

    public static Matcher<List<Product>> isCategoryExists() {
        return new IsCategoryExists();
    }

    @Override
    protected boolean matchesSafely(List<Product> products) {
        try {
            for(Product product : products) {
                CategoryType.valueOf(product.getCategoryTitle().toUpperCase());
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No such category in our dictionary");
    }
}