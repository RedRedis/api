package ru.learnup.javaqa.api.product;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.learnup.javaqa.api.dto.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

public class ProductGetTests {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String PRODUCT_ENDPOINT_ID = "/products/{id}";
    static final String PRODUCT_ENDPOINT = "/products";

    static final String productPositiveTest = "src/test/resources/productTest/getProductPositiveTest.csv";
    static final String productNegativeTest = "src/test/resources/productTest/getProductNegativeTest.csv";

    static final Faker faker = new Faker();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream(propertiesFile));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    void getAllProductPositiveTest() {
        given()
                .when()
                .get(PRODUCT_ENDPOINT)
                //.prettyPeek()
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvFileSource(files=productPositiveTest)
    void getProductPositiveTest(int id) {
        given()
                .when()
                .get(PRODUCT_ENDPOINT_ID, id)
                //.prettyPeek()
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvFileSource(files=productNegativeTest)
    void getProductNegativeTest(int id) {
        given()
                .when()
                .get(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(404);
    }

    @Test
    void fullCycle() {
        Product product = Product.builder()
                .title(faker.food().dish())
                .price(5000)
                .categoryTitle(FOOD.getName())
                .build();

        int id = given()
                .body(product)
                .header("Content-Type", "application/json")
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .jsonPath()
                .get("id");

        product.setId(id);

        given()
                .when()
                .get(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(200)
                .body("id", equalTo(product.getId()))
                .body("title", equalTo(product.getTitle()))
                .body("price", equalTo(product.getPrice()))
                .body("categoryTitle", equalTo(product.getCategoryTitle()));

        when()
                .delete(PRODUCT_ENDPOINT_ID, product.getId())
                .then()
                .statusCode(200);
    }

}
