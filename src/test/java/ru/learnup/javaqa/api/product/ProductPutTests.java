package ru.learnup.javaqa.api.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.CategoryType.*;
import static ru.learnup.javaqa.api.enums.SentProductType.*;


@Epic("Tests for products")
@Story("Put products tests")
public class ProductPutTests extends BaseTest {

    private static Integer ID;

    @BeforeAll
    static void iniTestProduct() {

        iniProduct();
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");

    }


    @Test
    @Description("Можно изменить поля продукта, кроме id")
    @Severity(SeverityLevel.CRITICAL)
    void putProductChangeTest() {

        iniProductId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя изменить несуществующий продукты")
    @Severity(SeverityLevel.NORMAL)
    void putProductNotExistingIdTest() {

        iniProductId(101010101);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя изменить продукт без id")
    @Severity(SeverityLevel.CRITICAL)
    void putProductNoIdTest() {

        iniProduct();
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя изменить продукт без id")
    @Severity(SeverityLevel.CRITICAL)
    void putProductNullTitleTest() {

        iniProductTitle(null);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Можно изменить title продукта на пустой")
    @Severity(SeverityLevel.MINOR)
    void putProductEmptyTitleTest() {

        iniProductTitle("");
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Поле title не должно изменить, если не указать его в json")
    @Severity(SeverityLevel.NORMAL)
    void putProductWithoutTitleTest() {

        iniProduct();
        product.setId(ID);
        iniProductRequestSpec(WITHOUT_TITLE);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Можно изменить продукт не указав поле price")
    @Severity(SeverityLevel.NORMAL)
    void putProductPriceNullTest() {

        iniProductPrice(null);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Можно изменить поле price")
    @Severity(SeverityLevel.CRITICAL)
    void putProductPriceZeroTest() {

        iniProductPrice(0);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Поле price не должно изменить, если не указать его в json")
    @Severity(SeverityLevel.NORMAL)
    void putProductNoPriceTest() {

        iniProduct();
        product.setId(ID);
        iniProductRequestSpec(WITHOUT_PRICE);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя именить поле price на отрицательное")
    @Severity(SeverityLevel.CRITICAL)
    void putProductNegativePriceTest() {

        iniProductPrice(-500);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Можно изменить категорию на сущестующую")
    @Severity(SeverityLevel.NORMAL)
    void putProductChangeCategoryTest() {

        iniProduct();
        product.setId(ID);
        product.setCategoryTitle(ELECTRONIC.getName());
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя именить категории, если она не указана")
    @Severity(SeverityLevel.CRITICAL)
    void putProductNullCategoryTest() {

        iniProduct();
        product.setId(ID);
        product.setCategoryTitle(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя именить категории, если она не существуеты")
    @Severity(SeverityLevel.CRITICAL)
    void putProductNotExistingCategoryTest() {

        iniProduct();
        product.setId(ID);
        product.setCategoryTitle("Хомяки");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @AfterAll
    static void tearDown() {
        given()
                .response()
                .spec(deleteResponseSpec)
                .when()
                .delete(PRODUCT_ENDPOINT_ID, ID);
    }
}
