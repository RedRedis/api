package ru.learnup.javaqa.api.product;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.learnup.javaqa.api.dto.Product;
import ru.learnup.javaqa.api.enums.CategoryType;

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

    static Product product;

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream(propertiesFile));
        RestAssured.baseURI = properties.getProperty("baseURL");

        product = Product.builder()
                .title(faker.food().dish())
                .price(5000)
                .categoryTitle(FOOD.getName())
                .build();
    }

    @Test
    void getAllProductPositiveTest() {
        when()
                .get(PRODUCT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvFileSource(files=productPositiveTest)
    void getProductPositiveTest(int id, String title, int price, String categoryTypeName) {
        when()
                .get(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("price", equalTo(price))
                .body("categoryTitle", equalTo(categoryTypeName));
    }

    @ParameterizedTest
    @CsvFileSource(files=productNegativeTest)
    void getProductNegativeTest(int id) {
        when()
                .get(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(404);
    }

    @Test
    void fullCycle() {

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

        when()
                .get(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(200)
                .body("id", equalTo(product.getId()))
                .body("title", equalTo(product.getTitle()))
                .body("price", equalTo(product.getPrice()))
                .body("categoryTitle", equalTo(product.getCategoryTitle()));

        when()
                .delete(PRODUCT_ENDPOINT_ID, id)
                .then()
                .statusCode(200);
    }

}
