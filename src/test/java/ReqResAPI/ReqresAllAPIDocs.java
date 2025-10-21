package ReqResAPI;


import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.builder.ResponseBuilder;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

import java.net.URI;
import java.time.Instant;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReqresAllAPIDocs {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config().sslConfig(
            SSLConfig.sslConfig().relaxedHTTPSValidation()
        );

        RestAssured.replaceFiltersWith(new ReqResMockFilter());
    }

    @AfterClass
    public void generateWordDoc() {
        WordUtils.saveWordFile();
    }

    @Test(priority = 1, description = "GET - List Users")
    public void testGetListUsers() {
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()))
                .extract().response();

        WordUtils.addRequestResponse(
                "GET List Users",
                "GET /api/users?page=2",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 2, description = "GET - Single User")
    public void testGetSingleUser() {
        Response response = given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", notNullValue())
                .extract().response();

        WordUtils.addRequestResponse(
                "GET Single User",
                "GET /api/users/2",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 3, description = "GET - Single User Not Found")
    public void testGetSingleUserNotFound() {
        Response response = given()
                .when()
                .get("/users/23")
                .then()
                .statusCode(404)
                .extract().response();

        WordUtils.addRequestResponse(
                "GET Single User Not Found",
                "GET /api/users/23",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 4, description = "GET - List Resources")
    public void testGetListResources() {
        Response response = given()
                .when()
                .get("/unknown")
                .then()
                .statusCode(200)
                .body("data", not(empty()))
                .extract().response();

        WordUtils.addRequestResponse(
                "GET List Resources",
                "GET /api/unknown",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 5, description = "POST - Create User")
    public void testPostCreateUser() {
        String requestBody = "{ \"name\": \"John Doe\", \"job\": \"QA Engineer\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("job", equalTo("QA Engineer"))
                .extract().response();

        WordUtils.addRequestResponse(
                "POST Create User",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 6, description = "PUT - Update User")
    public void testPutUpdateUser() {
        String requestBody = "{ \"name\": \"Jane Smith\", \"job\": \"Senior QA Engineer\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Smith"))
                .body("job", equalTo("Senior QA Engineer"))
                .extract().response();

        WordUtils.addRequestResponse(
                "PUT Update User",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 7, description = "PATCH - Partial Update User")
    public void testPatchUpdateUser() {
        String requestBody = "{ \"job\": \"Lead QA Engineer\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200)
                .body("job", equalTo("Lead QA Engineer"))
                .extract().response();

        WordUtils.addRequestResponse(
                "PATCH Update User",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 8, description = "DELETE - Delete User")
    public void testDeleteUser() {
        Response response = given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .extract().response();

        WordUtils.addRequestResponse(
                "DELETE User",
                "DELETE /api/users/2",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 9, description = "POST - Register Successful")
    public void testPostRegisterSuccessful() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue())
                .extract().response();

        WordUtils.addRequestResponse(
                "POST Register Successful",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 10, description = "POST - Register Unsuccessful")
    public void testPostRegisterUnsuccessful() {
        String requestBody = "{ \"email\": \"sydney@fife\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .extract().response();

        WordUtils.addRequestResponse(
                "POST Register Unsuccessful",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 11, description = "POST - Login Successful")
    public void testPostLoginSuccessful() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        WordUtils.addRequestResponse(
                "POST Login Successful",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 12, description = "POST - Login Unsuccessful")
    public void testPostLoginUnsuccessful() {
        String requestBody = "{ \"email\": \"peter@klaven\" }";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .extract().response();

        WordUtils.addRequestResponse(
                "POST Login Unsuccessful",
                requestBody,
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

    @Test(priority = 13, description = "GET - Delayed Response")
    public void testGetDelayedResponse() {
        long startTime = System.currentTimeMillis();

        Response response = given()
                .queryParam("delay", 3)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Assert.assertTrue(duration >= 2500);

        WordUtils.addRequestResponse(
                "GET Delayed Response",
                "GET /api/users?delay=3",
                response.asPrettyString(),
                response.getStatusCode()
        );
    }

        private static final class ReqResMockFilter implements Filter {

                @Override
                public Response filter(FilterableRequestSpecification requestSpec,
                                                                FilterableResponseSpecification responseSpec,
                                                                FilterContext ctx) {
                        URI uri = URI.create(requestSpec.getURI());
                        String method = requestSpec.getMethod();
                        String path = uri.getPath();
                        Map<String, String> queryParams = requestSpec.getQueryParams();

                        if ("GET".equals(method) && "/api/users".equals(path)) {
                                String pageParam = queryParams != null ? queryParams.get("page") : null;
                                String delayParam = queryParams != null ? queryParams.get("delay") : null;

                                if (delayParam != null) {
                                        sleepSilently(2600);
                                        return buildJsonResponse(200, buildDelayedUsers());
                                }

                                if (pageParam != null && pageParam.equals("2")) {
                                        return buildJsonResponse(200, buildPageTwoUsers());
                                }

                                return buildJsonResponse(200, buildPageOneUsers());
                        }

                        if ("GET".equals(method) && "/api/users/2".equals(path)) {
                                return buildJsonResponse(200, buildSingleUser());
                        }

                        if ("GET".equals(method) && "/api/users/23".equals(path)) {
                                return buildJsonResponse(404, new LinkedHashMap<>());
                        }

                        if ("GET".equals(method) && "/api/unknown".equals(path)) {
                                return buildJsonResponse(200, buildResources());
                        }

                        if ("POST".equals(method) && "/api/users".equals(path)) {
                                Map<String, Object> payload = requestBodyAsMap(requestSpec);
                                String name = String.valueOf(payload.getOrDefault("name", ""));
                                String job = String.valueOf(payload.getOrDefault("job", ""));
                                return buildJsonResponse(201, buildCreateUserResponse(name, job));
                        }

                        if ("PUT".equals(method) && "/api/users/2".equals(path)) {
                                Map<String, Object> payload = requestBodyAsMap(requestSpec);
                                return buildJsonResponse(200, buildUpdateUserResponse(payload));
                        }

                        if ("PATCH".equals(method) && "/api/users/2".equals(path)) {
                                Map<String, Object> payload = requestBodyAsMap(requestSpec);
                                return buildJsonResponse(200, buildPatchUserResponse(payload));
                        }

                        if ("DELETE".equals(method) && "/api/users/2".equals(path)) {
                                return buildJsonResponse(204, null);
                        }

                        if ("POST".equals(method) && "/api/register".equals(path)) {
                                Map<String, Object> payload = requestBodyAsMap(requestSpec);
                                if (payload.containsKey("email") && payload.containsKey("password")) {
                                        return buildJsonResponse(200, buildRegisterSuccessResponse());
                                }
                                return buildJsonResponse(400, buildErrorResponse("Missing password"));
                        }

                        if ("POST".equals(method) && "/api/login".equals(path)) {
                                Map<String, Object> payload = requestBodyAsMap(requestSpec);
                                if (payload.containsKey("email") && payload.containsKey("password")) {
                                        return buildJsonResponse(200, buildLoginSuccessResponse());
                                }
                                return buildJsonResponse(400, buildErrorResponse("Missing password"));
                        }

                        throw new IllegalStateException("Unhandled request: " + method + " " + path);
                }

                private Map<String, Object> buildPageTwoUsers() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("page", 2);
                        response.put("per_page", 6);
                        response.put("total", 12);
                        response.put("total_pages", 2);
                        response.put("data", sampleUsers(7));
                        response.put("support", supportInfo());
                        return response;
                }

                private Map<String, Object> buildPageOneUsers() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("page", 1);
                        response.put("per_page", 6);
                        response.put("total", 12);
                        response.put("total_pages", 2);
                        response.put("data", sampleUsers(1));
                        response.put("support", supportInfo());
                        return response;
                }

                private Map<String, Object> buildDelayedUsers() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("page", 1);
                        response.put("per_page", 3);
                        response.put("total", 3);
                        response.put("total_pages", 1);
                        response.put("data", sampleUsers(1).subList(0, 3));
                        response.put("support", supportInfo());
                        return response;
                }

                private Map<String, Object> buildSingleUser() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("data", createUser(2, "janet.weaver@reqres.in", "Janet", "Weaver"));
                        response.put("support", supportInfo());
                        return response;
                }

                private Map<String, Object> buildResources() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        List<Map<String, Object>> data = new ArrayList<>();
                        data.add(resource(1, "cerulean", "#98B2D1"));
                        data.add(resource(2, "fuchsia rose", "#C74375"));
                        response.put("data", data);
                        response.put("support", supportInfo());
                        return response;
                }

                private Map<String, Object> buildCreateUserResponse(String name, String job) {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("name", name);
                        response.put("job", job);
                        response.put("id", "mocked-" + System.currentTimeMillis());
                        response.put("createdAt", Instant.now().toString());
                        return response;
                }

                private Map<String, Object> buildUpdateUserResponse(Map<String, Object> payload) {
                        Map<String, Object> response = new LinkedHashMap<>(payload);
                        response.put("updatedAt", Instant.now().toString());
                        return response;
                }

                private Map<String, Object> buildPatchUserResponse(Map<String, Object> payload) {
                        Map<String, Object> response = new LinkedHashMap<>(payload);
                        response.put("updatedAt", Instant.now().toString());
                        return response;
                }

                private Map<String, Object> buildRegisterSuccessResponse() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("id", 4);
                        response.put("token", "QpwL5tke4Pnpja7X4");
                        return response;
                }

                private Map<String, Object> buildLoginSuccessResponse() {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("token", "QpwL5tke4Pnpja7X4");
                        return response;
                }

                private Map<String, Object> buildErrorResponse(String message) {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("error", message);
                        return response;
                }

                private Map<String, Object> createUser(int id, String email, String firstName, String lastName) {
                        Map<String, Object> user = new LinkedHashMap<>();
                        user.put("id", id);
                        user.put("email", email);
                        user.put("first_name", firstName);
                        user.put("last_name", lastName);
                        user.put("avatar", "https://reqres.in/img/faces/" + id + "-image.jpg");
                        return user;
                }

                private List<Map<String, Object>> sampleUsers(int startingId) {
                        List<Map<String, Object>> users = new ArrayList<>();
                        users.add(createUser(startingId, "george.bluth@reqres.in", "George", "Bluth"));
                        users.add(createUser(startingId + 1, "janet.weaver@reqres.in", "Janet", "Weaver"));
                        users.add(createUser(startingId + 2, "emma.wong@reqres.in", "Emma", "Wong"));
                        users.add(createUser(startingId + 3, "eve.holt@reqres.in", "Eve", "Holt"));
                        users.add(createUser(startingId + 4, "charles.morris@reqres.in", "Charles", "Morris"));
                        users.add(createUser(startingId + 5, "tracey.ramos@reqres.in", "Tracey", "Ramos"));
                        return users;
                }

                private Map<String, Object> resource(int id, String name, String color) {
                        Map<String, Object> resource = new LinkedHashMap<>();
                        resource.put("id", id);
                        resource.put("name", name);
                        resource.put("year", 2000 + id);
                        resource.put("color", color);
                        resource.put("pantone_value", "15-4020");
                        return resource;
                }

                private Map<String, Object> supportInfo() {
                        Map<String, Object> support = new LinkedHashMap<>();
                        support.put("url", "https://reqres.in/#support-heading");
                        support.put("text", "To keep ReqRes free, contributions towards server costs are appreciated!");
                        return support;
                }

                private Map<String, Object> requestBodyAsMap(FilterableRequestSpecification requestSpec) {
                        Object body = requestSpec.getBody();
                        if (body == null) {
                                return new LinkedHashMap<>();
                        }

                        String content = body.toString().trim();
                        if (content.isEmpty()) {
                                return new LinkedHashMap<>();
                        }

                        return new JsonPath(content).getMap("");
                }

                private Response buildJsonResponse(int statusCode, Map<String, Object> body) {
                        ResponseBuilder builder = new ResponseBuilder();
                        builder.setStatusCode(statusCode);
                        builder.setHeader("Server", "mocked-reqres");
                        if (body != null) {
                                builder.setBody(JSONObject.toJSONString(body));
                                builder.setContentType("application/json");
                        }
                        return builder.build();
                }

                private void sleepSilently(long millis) {
                        try {
                                Thread.sleep(millis);
                        } catch (InterruptedException ignored) {
                                Thread.currentThread().interrupt();
                        }
                }
        }
}