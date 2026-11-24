package com.saiprakash.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import com.saiprakash.api.base.BaseTest;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** CRUD and negative tests against the JSONPlaceholder /posts endpoint. */
public class PostsApiTest extends BaseTest {

    @Test(description = "GET all posts returns 100 records")
    public void getAllPosts_returns100Records() {
        given().spec(requestSpec)
                .when().get("/posts")
                .then().spec(responseSpec)
                .statusCode(200)
                .body("$", hasSize(100));
    }

    @Test(description = "GET a single post returns the expected fields")
    public void getSinglePost_returnsExpectedFields() {
        given().spec(requestSpec)
                .when().get("/posts/1")
                .then().spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test(description = "GET posts filtered by userId returns only that user's posts")
    public void getPostsByUser_returnsOnlyThatUser() {
        given().spec(requestSpec)
                .queryParam("userId", 1)
                .when().get("/posts")
                .then().spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", org.hamcrest.Matchers.everyItem(equalTo(1)));
    }

    @Test(description = "POST creates a new post and echoes the payload")
    public void createPost_returns201AndEchoesPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "automation testing");
        payload.put("body", "RestAssured with TestNG");
        payload.put("userId", 7);

        given().spec(requestSpec)
                .body(payload)
                .when().post("/posts")
                .then().spec(responseSpec)
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("automation testing"))
                .body("body", equalTo("RestAssured with TestNG"))
                .body("userId", equalTo(7));
    }

    @Test(description = "PUT updates an existing post")
    public void updatePost_returns200AndUpdatedValues() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 1);
        payload.put("title", "updated title");
        payload.put("body", "updated body");
        payload.put("userId", 1);

        given().spec(requestSpec)
                .body(payload)
                .when().put("/posts/1")
                .then().spec(responseSpec)
                .statusCode(200)
                .body("title", equalTo("updated title"))
                .body("body", equalTo("updated body"));
    }

    @Test(description = "DELETE removes a post")
    public void deletePost_returns200() {
        given().spec(requestSpec)
                .when().delete("/posts/1")
                .then().spec(responseSpec)
                .statusCode(200);
    }

    @Test(description = "GET a post that does not exist returns 404")
    public void getUnknownPost_returns404() {
        Response response = given().spec(requestSpec)
                .when().get("/posts/99999")
                .then().extract().response();

        Assert.assertEquals(response.statusCode(), 404, "Unexpected status for unknown post");
    }

    @DataProvider(name = "postIds")
    public Object[][] postIds() {
        return new Object[][] {{1}, {25}, {50}, {100}};
    }

    @Test(dataProvider = "postIds", description = "Data-driven: each valid id returns its own record")
    public void getPostById_dataDriven(int id) {
        given().spec(requestSpec)
                .pathParam("id", id)
                .when().get("/posts/{id}")
                .then().spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(id));
    }
}
