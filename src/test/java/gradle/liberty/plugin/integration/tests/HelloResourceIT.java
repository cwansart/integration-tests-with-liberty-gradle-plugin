package gradle.liberty.plugin.integration.tests;

import io.restassured.RestAssured;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class HelloResourceIT {

    public static final String URL = "http://localhost:9080/gradle-liberty-plugin-for-integration-tests/api/hello";

    @Test
    public void getHello() {
        RestAssured.when()
            .get(URL)
            .then()
            .assertThat()
            .statusCode(200)
            .body("hellos.size()", is(4));
    }
}
