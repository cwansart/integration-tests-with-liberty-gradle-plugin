package gradle.liberty.plugin.integration.tests;

import io.restassured.RestAssured;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;

public class HelloResourceIT {

    private static final Logger LOG = LoggerFactory.getLogger(HelloResourceIT.class);

    public static final String URL = "http://localhost:9080/gradle-liberty-plugin-for-integration-tests/api/hello";

    @Test
    public void getHello() {

        LOG.info("Calling HelloResourceIT.getHello() test.");

        RestAssured.when()
            .get(URL)
            .then()
            .assertThat()
            .statusCode(200)
            .body("hellos.size()", is(4));
    }
}
