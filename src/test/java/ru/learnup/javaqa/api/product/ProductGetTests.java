package ru.learnup.javaqa.api.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import io.qameta.allure.*;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.SentProductType.FULL;

@Epic("Tests for products")
@Story("Get products tests")
public class ProductGetTests extends BaseTest {

    @Test
    @Description("Возможно получить все продукты")
    @Severity(SeverityLevel.BLOCKER)
    void getAllProductPositiveTest() {
        given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT);
    }

    @ParameterizedTest
    @CsvFileSource(files=productPositiveTest)
    @Description("Возможно получить продукты с существующим id")
    @Severity(SeverityLevel.CRITICAL)
    void getProductPositiveTest(int id) {
        given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT_ID, id);
    }

    @ParameterizedTest
    @CsvFileSource(files=productNegativeTest)
    @Description("Невозможно получить продукты с несуществующим id")
    @Severity(SeverityLevel.NORMAL)
    void getProductNegativeTest(int id) {
        given()
                .response()
                .spec(negativeResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT_ID, id);
    }

    @Test
    @Description("Полный цикл работы с объектом: создание, получаение, удаление")
    @Severity(SeverityLevel.CRITICAL)
    void fullCycle() {

        iniProduct();
        iniProductRequestSpec(FULL);
        iniProductResponseSpec201();

        int id = given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");

        product.setId(id);
        iniProductResponseSpec200();

        given()
                .response()
                .spec(productResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT_ID, id);

        given()
                .response()
                .spec(deleteResponseSpec)
                .when()
                .delete(PRODUCT_ENDPOINT_ID, product.getId());
    }

}
