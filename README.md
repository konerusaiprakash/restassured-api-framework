# REST API Automation Framework (RestAssured + TestNG)

API test automation framework built with Java, RestAssured, TestNG and Maven, run against the public
[JSONPlaceholder](https://jsonplaceholder.typicode.com) API. Includes a Jenkins pipeline for scheduled runs.

## Tech stack
- Java 17
- RestAssured 5.x
- TestNG
- Maven
- Jenkins (Jenkinsfile included)

## What it covers
- GET, POST, PUT and DELETE requests with status code and response body validation
- Query parameters and path parameters
- Negative test (404 for a missing resource)
- Data-driven tests using TestNG `@DataProvider`
- Response time check through a shared response specification
- Reusable request/response specifications in `BaseTest`
- Environment values kept in `config.properties`

## Project structure
```
src/test/java/com/saiprakash/api
  base/BaseTest.java          shared request and response specs
  tests/PostsApiTest.java     API test cases
  utils/ConfigReader.java     reads config.properties
src/test/resources/config.properties
testng.xml
Jenkinsfile
pom.xml
```

## How to run
Requirements: JDK 17 and Maven installed.

```
mvn clean test
```

Reports are generated in `target/surefire-reports`.

## Jenkins
Create a Pipeline job pointing at this repository. The `Jenkinsfile` runs `mvn clean test` on a
weekday nightly schedule and publishes the test results.

## Author
Koneru Loka Saiprakash
