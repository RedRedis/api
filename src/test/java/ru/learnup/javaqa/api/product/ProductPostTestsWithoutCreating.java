package ru.learnup.javaqa.api.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.enums.SentProductType.*;

public class ProductPostTestsWithoutCreating extends BaseTest {

    @ParameterizedTest
    @CsvFileSource(files=productPostExistingIdTest)
    void postExistingProductId(int id) {

        iniProductId(id);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postNotValidProduct() {

        iniProductRequestSpec();
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postNoExistingProductId() {

        iniProductId(1010101);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postNoExistingProductCategory() {

        iniProductCategoryTitle("Problem");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postNullProductCategory() {

        iniProductCategoryTitle(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postEmptyProductCategory() {

        iniProductCategoryTitle("");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);

    }

    @Test
    void postNullProductTitle() {

        iniProductTitle(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postEmptyProductTitle() {

        iniProductTitle("");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);

    }

    @Test
    void postNegativeProductPrice() {

        iniProductPrice(-500);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postNumberInsteadOfStringProductTitle() {

        iniProductTitle(6000);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postWithoutFieldProductPrice() {

        iniProduct();
        iniProductRequestSpec(WITHOUT_PRICE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postWithoutFieldProductTitle() {

        iniProduct();
        iniProductRequestSpec(WITHOUT_TITLE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postOnlyFieldProductCategoryTitle() {

        iniProduct();
        iniProductRequestSpec(ONLY_CATEGORY_TITLE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    void postStringInsteadOfNumberProductPrice() {

        iniProductPrice("2000000");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }
}
