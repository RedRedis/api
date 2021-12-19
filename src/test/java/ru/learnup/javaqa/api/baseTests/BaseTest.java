package ru.learnup.javaqa.api.baseTests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import ru.learnup.javaqa.api.dto.*;
import ru.learnup.javaqa.api.enums.SentProductType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.restassured.filter.log.LogDetail.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static ru.learnup.javaqa.api.enums.CategoryType.FOOD;

public abstract class BaseTest {

    protected static final Properties properties = new Properties();

    protected static final String categoryPositiveTest = "src/test/resources/categoryTest/getCategoryPositiveTest.csv";
    protected static final String categoryNegativeTest = "src/test/resources/categoryTest/getCategoryNegativeTest.csv";

    protected static final String productPositiveTest = "src/test/resources/productTest/getProductPositiveTest.csv";
    protected static final String productNegativeTest = "src/test/resources/productTest/getProductNegativeTest.csv";

    protected static final String productPostExistingIdTest = "src/test/resources/productTest/postExistingProductId.csv";

    protected static final Faker faker = new Faker();
    protected static Product product;

    protected static RequestSpecification logRequestSpecification;
    protected static ResponseSpecification responseSpecification;

    protected static ResponseSpecification positiveResponseSpec;
    protected static ResponseSpecification negativeResponseSpec;

    protected static ResponseSpecification deleteResponseSpec;

    protected static RequestSpecification productRequestSpec;
    protected static ResponseSpecification productResponseSpec;

    @BeforeAll
    protected static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");

        logRequestSpecification = new RequestSpecBuilder()
                .log(METHOD)
                .log(URI)
                .log(BODY)
                .log(HEADERS)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(3000L), TimeUnit.MILLISECONDS)
                .build();

        RestAssured.requestSpecification = logRequestSpecification;
        RestAssured.responseSpecification = responseSpecification;

        positiveResponseSpec =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .build();

        negativeResponseSpec =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .expectStatusLine("HTTP/1.1 404 ")
                .build();

        deleteResponseSpec = new ResponseSpecBuilder()
                .expectContentType("")
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .build();
    }

    protected static void iniProductRequestSpec(SentProductType type) {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        switch (type) {
            case FULL:
                requestSpecBuilder.setBody(product);
                break;
            case WITHOUT_ID:
                requestSpecBuilder.setBody(new ProductWithoutId(product));
                break;
            case WITHOUT_PRICE:
                requestSpecBuilder.setBody(new ProductWithoutPrice(product));
                break;
            case WITHOUT_TITLE:
                requestSpecBuilder.setBody(new ProductWithoutTitle(product));
                break;
            case ONLY_CATEGORY_TITLE:
                requestSpecBuilder.setBody(new CategoryTitle(product));
                break;
        }

        productRequestSpec = requestSpecBuilder
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static void iniProductRequestSpec() {
        productRequestSpec = new RequestSpecBuilder()
                .setBody("{}")
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static void iniProductResponseSpec200() {
        productResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
    }

    protected static void iniProductResponseSpec201() {
        productResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
    }

    protected static void iniProductResponseSpec400() {
        productResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .build();
    }

    protected static void iniProductResponseSpec500() {
        productResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(500)
                .expectStatusLine("HTTP/1.1 500 ")
                .build();
    }

    protected static void iniProduct() {
        product = Product.builder()
                .title(faker.food().dish())
                .price(5000)
                .categoryTitle(FOOD.getName())
                .build();
    }

    protected static void iniProductPrice(Object price) {
        product = Product.builder()
                .title(faker.food().dish())
                .price(price)
                .categoryTitle(FOOD.getName())
                .build();
    }

    protected static void iniProductTitle(Object title) {
        product = Product.builder()
                .title(title)
                .price(5000)
                .categoryTitle(FOOD.getName())
                .build();
    }

    protected static void iniProductId(int id) {
        product = Product.builder()
                .id(id)
                .title(faker.food().dish())
                .price(5000)
                .categoryTitle(FOOD.getName())
                .build();
    }

    protected static void iniProductCategoryTitle(String categoryTitle) {
        product = Product.builder()
                .title(faker.food().dish())
                .price(5000)
                .categoryTitle(categoryTitle)
                .build();
    }
}
