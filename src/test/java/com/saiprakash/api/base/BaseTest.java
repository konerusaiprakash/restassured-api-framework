package com.saiprakash.api.base;

import com.saiprakash.api.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;

/** Shared request/response specifications for all API tests. */
public class BaseTest {

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass(alwaysRun = true)
    public void setUpSpecs() {
        RestAssured.useRelaxedHTTPSValidation();

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("base.uri"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(Matchers.lessThan(ConfigReader.getLong("response.time.max.ms")))
                .build();
    }
}
