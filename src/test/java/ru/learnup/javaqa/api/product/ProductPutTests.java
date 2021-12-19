package ru.learnup.javaqa.api.product;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.CategoryType.*;
import static ru.learnup.javaqa.api.enums.SentProductType.*;

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
    void putProductChangeTest() {

        iniProductId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductNotExistingIdTest() {

        iniProductId(101010101);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductNoIdTest() {

        iniProduct();
        iniProductRequestSpec(FULL);

        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductNullTitleTest() {

        iniProductTitle(null);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductEmptyTitleTest() {

        iniProductTitle("");
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductWithoutTitleTest() {

        iniProduct();
        product.setId(ID);
        iniProductRequestSpec(WITHOUT_TITLE);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductPriceNullTest() {

        iniProductPrice(null);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductPriceZeroTest() {

        iniProductPrice(0);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductNoPriceTest() {

        iniProduct();
        product.setId(ID);
        iniProductRequestSpec(WITHOUT_PRICE);
        iniProductResponseSpec200();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
    void putProductNegativePriceTest() {

        iniProductPrice(-500);
        product.setId(ID);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .put(PRODUCT_ENDPOINT);
    }

    @Test
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
