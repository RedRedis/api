package ru.learnup.javaqa.api.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.SentProductType.FULL;
import static ru.learnup.javaqa.api.enums.SentProductType.WITHOUT_ID;


@Epic("Tests for products")
@Story("Post products tests")
public class ProductPostTests extends BaseTest {

    private static Integer ID;

    @Test
    @Description("Создание продукта с id = null")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Отправка json продукта без поля id")
    @Severity(SeverityLevel.MINOR)
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
    @Description("Создание продукта с price = 0")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Создание продукта с price = null")
    @Severity(SeverityLevel.MINOR)
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
    @Description("Создание продукта с дробной ценой")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Создание продукта с ценой превышающий тип int")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Создание продукта с русским title")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Создание продукта с title в виде цифр")
    @Severity(SeverityLevel.TRIVIAL)
    void postOnlyNumberProductTitle() {

        iniProductTitle("5000");
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
