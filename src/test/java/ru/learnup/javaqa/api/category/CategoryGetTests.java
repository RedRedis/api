package ru.learnup.javaqa.api.category;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class CategoryGetTests {

    static final Properties properties = new Properties();

    static final String propertiesFile = "src/test/resources/application.properties";
    static final String CATEGORY_ENDPOINT = "/categories/{id}";

    static final String categoryPositiveTest = "src/test/resources/categoryTest/getCategoryPositiveTest.csv";
    static final String categoryNegativeTest = "src/test/resources/categoryTest/getCategoryNegativeTest.csv";

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream(propertiesFile));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @ParameterizedTest
    @CsvFileSource(files=categoryPositiveTest)
    void getCategoryPositiveTest(int id) {
        given()
                .when()
                .get(CATEGORY_ENDPOINT, id)
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvFileSource(files=categoryNegativeTest)
    void getCategoryNegativeTest(int id) {
        given()
                .when()
                .get(CATEGORY_ENDPOINT, id)
                .then()
                .statusCode(404);
    }
}
