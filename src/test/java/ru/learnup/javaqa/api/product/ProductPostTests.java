package ru.learnup.javaqa.api.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.SentProductType.FULL;
import static ru.learnup.javaqa.api.enums.SentProductType.WITHOUT_ID;

public class ProductPostTests extends BaseTest {

    private static Integer ID;

    @Test
    void postNullProductId() {

        iniProduct();
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postWithoutFieldProductId() {

        iniProduct();
        iniProductRequestSpec(WITHOUT_ID);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postZeroProductPrice() {

        iniProductPrice(0);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postNullProductPrice() {

        iniProductPrice(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }


    @Test
    void postDoubleProductPrice() {

        iniProductPrice(100.456);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postHugeProductPrice() {

        iniProductPrice(4_000_000_000L);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postRusProductTitle() {

        iniProductTitle("Хлебушек");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postOnlyNumberProductTitle() {

        iniProductTitle("5000");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postSeparatedProductPrice() {

        iniProductPrice("500 000");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }
    @Test
    void postSeparatedDoubleProductPrice() {

        iniProductPrice("500 000,937");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postSeparatedProductPriceWithPoint() {

        iniProductPrice("500.000");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postSeparatedDoubleProductPriceWithPoint() {

        iniProductPrice("500.000,657");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        ID = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }


    @AfterEach
    void tearDown() {
        if (ID != null) {
            given()
                    .response()
                    .spec(deleteResponseSpec)
                    .when()
                    .delete(PRODUCT_ENDPOINT_ID, ID);
        }
    }
}
