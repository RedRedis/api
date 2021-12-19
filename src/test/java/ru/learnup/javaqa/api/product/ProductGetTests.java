package ru.learnup.javaqa.api.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT_ID;
import static ru.learnup.javaqa.api.enums.SentProductType.FULL;

public class ProductGetTests extends BaseTest {

    @Test
    void getAllProductPositiveTest() {
        given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT);
    }

    @ParameterizedTest
    @CsvFileSource(files=productPositiveTest)
    void getProductPositiveTest(int id) {
        given()
                .response()
                .spec(positiveResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT_ID, id);
    }

    @ParameterizedTest
    @CsvFileSource(files=productNegativeTest)
    void getProductNegativeTest(int id) {
        given()
                .response()
                .spec(negativeResponseSpec)
                .when()
                .get(PRODUCT_ENDPOINT_ID, id);
    }

    @Test
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
