package ru.learnup.javaqa.api.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.dto.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ProductPostTestsWithoutCreating {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String PRODUCT_ENDPOINT = "/products";

    static Product product;

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream(propertiesFile));
        RestAssured.baseURI = properties.getProperty("baseURL");

        product = Product.builder()
                .title("Burger")
                .price(500)
                .categoryTitle("Food")
                .build();
    }

    @Test
    void postExistingProductId() {

        product.setId(1);

        given()
                .body(product.toString())
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
                .body(product.toString())
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
                .body(product.toString())
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
                .body(product.toString())
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
                .body(product.toString())
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
                .body(product.toString())
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
                .body(product.toString())
                .header("Content-Type", "application/json")
                .when()
                .post(PRODUCT_ENDPOINT)
                .then()
                .statusCode(500);
    }
}
