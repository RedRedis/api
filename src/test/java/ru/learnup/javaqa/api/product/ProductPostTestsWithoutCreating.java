package ru.learnup.javaqa.api.product;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.dto.CategoryTitle;
import ru.learnup.javaqa.api.dto.Product;
import ru.learnup.javaqa.api.dto.ProductWithoutPrice;
import ru.learnup.javaqa.api.dto.ProductWithoutTitle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

public class ProductPostTestsWithoutCreating {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String PRODUCT_ENDPOINT = "/products";

    static final Faker faker = new Faker();

    static Product product;

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream(propertiesFile));
        RestAssured.baseURI = properties.getProperty("baseURL");

        product = Product.builder()
                .title(faker.food().dish())
                .price(500)
                .categoryTitle(FOOD.getName())
                .build();
    }

    @Test
    void postExistingProductId() {

        product.setId(FOOD.getId());

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    void postNotValidProduct() {
        given()
                .body("{}")
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postNoExistingProductId() {

        product.setId(1010101);

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    void postNoExistingProductCategory() {

        product.setCategoryTitle("Problem");

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                //.prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    void postNullProductCategory() {

        product.setCategoryTitle(null);

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                //.prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    void postEmptyProductCategory() {

        product.setCategoryTitle("");

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                //.prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    void postNullProductTitle() {

        product.setTitle(null);

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postEmptyProductTitle() {

        product.setTitle("");

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postNegativeProductPrice() {

        product.setPrice(-500);

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postNumberInsteadOfStringProductTitle() {

        product.setTitle(6000);

        given()
                .body(product)
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postWithoutFieldProductPrice() {
        given()
                .body(new ProductWithoutPrice(product))
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postWithoutFieldProductTitle() {
        given()
                .body(new ProductWithoutTitle(product))
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    void postOnlyFieldProductCategoryTitle() {
        given()
                .body(new CategoryTitle(product))
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }
    
}
