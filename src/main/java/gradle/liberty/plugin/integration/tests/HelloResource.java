package gradle.liberty.plugin.integration.tests;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Response getHello() {
        Map<String, List<String>> hellos = Collections.singletonMap("hellos", Arrays.asList("Hallo", "Hello", "Hola",
            "こんにちは"));
        return Response.ok(hellos).build();
    }
}
