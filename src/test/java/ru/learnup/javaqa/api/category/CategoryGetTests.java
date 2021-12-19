package ru.learnup.javaqa.api.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import io.qameta.allure.*;
import ru.learnup.javaqa.api.baseTests.BaseTest;
import ru.learnup.javaqa.api.dto.Category;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.learnup.javaqa.api.Endpoints.CATEGORY_ENDPOINT;
import static ru.learnup.javaqa.api.asserts.IsCategoryExists.isCategoryExists;
import static ru.learnup.javaqa.api.asserts.IsCategoryFood.isCategoryFood;
import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

@Epic("Tests for categories")
@Story("Get categories tests")
public class CategoryGetTests extends BaseTest {

    @ParameterizedTest
    @CsvFileSource(files=categoryPositiveTest)
    @Description("Возможно получить существующие категории")
    @Severity(SeverityLevel.BLOCKER)
    void getCategoryPositiveTest(int id) {
        given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, id);
    }

    @ParameterizedTest
    @CsvFileSource(files=categoryNegativeTest)
    @Description("Нельзя получить несуществующие категории")
    @Severity(SeverityLevel.NORMAL)
    void getCategoryNegativeTest(int id) {
        given()
                .response()
                .spec(negativeResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, id);
    }

    @Test
    @Description("Проверка соотвествия продуктов категории 'Food'")
    @Severity(SeverityLevel.CRITICAL)
    void getCheckCategoryProductTest () {
        Category response = given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .body()
                .as(Category.class);

        assertThat(response.getProducts(), isCategoryExists());
        assertThat(response.getProducts(), isCategoryFood());
        assertEquals(response.getTitle(), FOOD.getName());
        assertEquals(response.getId(), FOOD.getId());
    }
}
