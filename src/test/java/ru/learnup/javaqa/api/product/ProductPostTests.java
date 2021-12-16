package ru.learnup.javaqa.api.product;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.learnup.javaqa.api.dto.Product;
import ru.learnup.javaqa.api.dto.ProductWithoutId;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

public class ProductPostTests {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String PRODUCT_ENDPOINT_ID = "/products/{id}";
    static final String PRODUCT_ENDPOINT = "/products";

    static final Faker faker = new Faker();

    static Integer ID;
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
    void postNullProductId() {
        ID = given()
                .body(product)
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
                .body(new ProductWithoutId(product))
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
                .body(product)
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
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postDoubleProductPrice() {

        product.setPrice(100.456);

        ID = given()
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .body("price", equalTo(product.getPrice()))
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postHugeProductPrice() {

        product.setPrice(4_000_000_000L);

        ID = given()
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .body("price", equalTo(product.getPrice()))
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postRusProductTitle() {

        product.setTitle("Хлебушек");

        ID = given()
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .body("title", equalTo(product.getTitle()))
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");
    }

    @Test
    void postOnlyNumberProductTitle() {

        product.setTitle("5000");

        ID = given()
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .body("title", equalTo(product.getTitle()))
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
