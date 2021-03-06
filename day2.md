
# Day 2 - Architecture training

Creating automated tests for web APIs.

## Materials & Resources

| Material                                                                            |  Time |
| :---------------------------------------------------------------------------------- | ----: |
| [Test a REST API with rest-assured](https://www.youtube.com/watch?v=PAyGma2OMFo)    |  4:23 |
| [RestAssured API Automation Framework](https://www.youtube.com/watch?v=AbJrfP4ziIk) | 21:11 |
| [A Guide to REST-assured](https://www.baeldung.com/rest-assured-tutorial)           |  read |

## Material Review

- Why do we use mocks?
- How to use Mockito?
- What are unit vs. integration test in a web application?
- How to create a test which calls a Web API?
  - How to use the `TestRestTemplate`?
- How to use REST Assured library?
  - What is Given, When, Then (Gherkin language)?
  - What are the benefits of using it?
  - What is JsonPath?
  - How to debug during test development?<!--
      You can use `.log().all()`
  -->
  - How to measure and assert the response time?
- What are the most common Hamcrest matchers?
<!--
  - equalTo, hasProperty, empty, hasSize, contains, containsString, nullValue, greaterThan, lessThan
-->
- How to mock certain parts of the application during tests?
- How to use in-memory database instead of the real one?
  - What are the benefits of doing so?
- What's JSON Schema validation?
- How to test an API with authentication?
- How to get the response object?
- (Optional) What are end-to-end test?
  - What's Selenium? How to get started?
  - What's a Page Model?

## Workshop

### Mocking

Here is a [simple Java app](./workshop/mockingpunkapi) using the Punk API. The `BeerStatistics` class which depends on the `PunkAPI` class and the task is to create a few unit tests for the class using Mockito.

### Testing REST APIs

Open the RedditAPI project from yesterday. You're going to create several tests today for this project.

### Add REST Assured library

Add the dependencies to the `gradle.build` file.

```groovy
dependencies {
    ...
    testCompile 'io.rest-assured:rest-assured:4.0.0'
    testCompile 'io.rest-assured:json-path:4.0.0'
    testCompile 'io.rest-assured:xml-path:4.0.0'
}
```

Then you can create your first test:

```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MyRedditApiApplicationTests {

  @LocalServerPort
  int port;

  @Before
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  public void contextLoads() {
    get("/api")
      .then()
      .body("subredditsUrl", equalTo("http://localhost:8080/api/subreddits"));
  }
}
```

Let's cover the web application used the first day with several kind of integration tests.

First let's create a test which tests the whole application if it's running or not.

In the following test let's use an in-memory database and prepare fake data to run the test.

In the following test use the REST Assured library for the following scenarios:

- The result contains the expected number of objects
- The returned objects have the correct values

Let's create a test where you mock the whole repository layer so there is no real database call.

## Measure and assert response time

Create a test to assert that the response time is in within a certain time range.

```java
when().
      get("/lotto").
then().
      time(lessThan(2000L)); // Milliseconds
```

## Use JSON Schema validation

Add the dependencies to the `gradle.build` file.

```
    testCompile 'io.rest-assured:json-schema-validator:4.0.0'
```

JSON Schema is a description of the JSON data structure, you can generate one online, e.g. at https://jsonschema.net/.

After you have the JSON Schema in your project you can use JSON Schema Validation:

```java
get("/api/posts")
  .then()
  .assertThat()
  .body(matchesJsonSchemaInClasspath("posts-schema.json"));
```

## Test the login endpoint

Create a test which calls the login endpoint then check if the current user is successfully logged in.
Create another test which calls the current user API without login and check if the response is 401.

### Login

`POST /login`

Payload:

```json
{
  "name": "Petike"
}
```

Response:

- `200 OK`

```json
{
    "authToken": "732ff80f-3630-400c-a377-a238f9f977c4"
}
```

### Get currently logged in User

Expects the auth token in the `X-AUTH-TOKEN` header.

`GET /api/users/me`

Request Headers:

`X-AUTH-TOKEN: 732ff80f-3630-400c-a377-a238f9f977c4`

Response:

- `200 OK`

```json
{
    "id": 1,
    "name": "Petike"
}
```

OR

- `401 Unauthorized`

### Test the Votes API 💪

The votes API requires a logged in user.

`PUT /api/posts/{id}/vote`

**Request Headers**

`Content-Type: application/json`

**Request Payload**

```json
{
    "direction": "up"|"down"
}
```

**Response**

- `204 No Content` if succeeded
- `400 Bad Request` if `id` or `direction` is not valid
- `403 Forbidden` if the user is not logged in
