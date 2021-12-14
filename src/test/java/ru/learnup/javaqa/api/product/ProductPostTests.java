package ru.learnup.javaqa.api.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.dto.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ProductPostTests {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String PRODUCT_ENDPOINT_ID = "/products/{id}";
    static final String PRODUCT_ENDPOINT = "/products";

    static Integer ID;
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
    void postNullProductId() {
        ID = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postWithoutFieldProductId() {
        ID = given()
                .body(product.toStringWithoutId())
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postZeroProductPrice() {

        product.setPrice(0);

        ID = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postNullProductPrice() {

        product.setPrice(null);

        ID = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }


    @AfterEach
    void tearDown() {
        when()
                .delete(PRODUCT_ENDPOINT_ID, ID)
                .then()
                .statusCode(200);
    }
}
