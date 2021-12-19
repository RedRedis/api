package ru.learnup.javaqa.api.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.learnup.javaqa.api.baseTests.BaseTest;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.Endpoints.PRODUCT_ENDPOINT;
import static ru.learnup.javaqa.api.enums.SentProductType.*;

@Epic("Tests for products")
@Story("Post products tests")
public class WithoutCreatingProductPostTests extends BaseTest {

    @ParameterizedTest
    @CsvFileSource(files=productPostExistingIdTest)
    @Description("Нельзя создать продукт с существующим id")
    @Severity(SeverityLevel.CRITICAL)
    void postExistingProductId(int id) {

        iniProductId(id);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с невалидным json")
    @Severity(SeverityLevel.BLOCKER)
    void postNotValidProduct() {

        iniProductRequestSpec();
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с несуществующим id")
    @Severity(SeverityLevel.CRITICAL)
    void postNoExistingProductId() {

        iniProductId(1010101);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec400();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с несуществующей категорией")
    @Severity(SeverityLevel.CRITICAL)
    void postNoExistingProductCategory() {

        iniProductCategoryTitle("Problem");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с категорией = null")
    @Severity(SeverityLevel.CRITICAL)
    void postNullProductCategory() {

        iniProductCategoryTitle(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с пустой категорией")
    @Severity(SeverityLevel.CRITICAL)
    void postEmptyProductCategory() {

        iniProductCategoryTitle("");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);

    }

    @Test
    @Description("Нельзя создать продукт с title = null")
    @Severity(SeverityLevel.NORMAL)
    void postNullProductTitle() {

        iniProductTitle(null);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с пустым title")
    @Severity(SeverityLevel.NORMAL)
    void postEmptyProductTitle() {

        iniProductTitle("");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);

    }

    @Test
    @Description("Нельзя создать продукт с отрицательной ценой")
    @Severity(SeverityLevel.CRITICAL)
    void postNegativeProductPrice() {

        iniProductPrice(-500);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с цифровым title вместо строкового")
    @Severity(SeverityLevel.TRIVIAL)
    void postNumberInsteadOfStringProductTitle() {

        iniProductTitle(6000);
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт без price в json")
    @Severity(SeverityLevel.NORMAL)
    void postWithoutFieldProductPrice() {

        iniProduct();
        iniProductRequestSpec(WITHOUT_PRICE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт без title в json")
    @Severity(SeverityLevel.NORMAL)
    void postWithoutFieldProductTitle() {

        iniProduct();
        iniProductRequestSpec(WITHOUT_TITLE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт только с категорией в json")
    @Severity(SeverityLevel.BLOCKER)
    void postOnlyFieldProductCategoryTitle() {

        iniProduct();
        iniProductRequestSpec(ONLY_CATEGORY_TITLE);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }

    @Test
    @Description("Нельзя создать продукт с строковым price вместо числового")
    @Severity(SeverityLevel.TRIVIAL)
    void postStringInsteadOfNumberProductPrice() {

        iniProductPrice("2000000");
        iniProductRequestSpec(FULL);
        iniProductResponseSpec500();

        given(productRequestSpec, productResponseSpec)
                .post(PRODUCT_ENDPOINT);
    }
}
