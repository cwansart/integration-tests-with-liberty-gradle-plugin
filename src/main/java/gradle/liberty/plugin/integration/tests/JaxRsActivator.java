package gradle.liberty.plugin.integration.tests;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class JaxRsActivator extends Application {
}
