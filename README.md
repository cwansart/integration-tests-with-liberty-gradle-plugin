# Integration Tests with liberty-gradle-plugin

This is an example project to show how to include the integration tests in the gradle build pipeline.

## Run the project

To run the project with integration tests run:

```bash
$ ./gradlew clean integration-test
```

## Integration into build.gradle

First, we need to add the `liberty-gradle-plugin`. We cannot use the modern approach
```groovy
plugins {
  id "io.openliberty.tools.gradle.Liberty" version "3.0"
}
```
**currently does not work**, this is a known bug, see: https://github.com/OpenLiberty/ci.gradle/issues/441

Thus we need to use the legacy way to include it:
```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'io.openliberty.tools:liberty-gradle-plugin:3.0'
    }
}
```

After that, add the runtime to the dependencies block:
```
dependencies {
    // ...
    libertyRuntime 'io.openliberty:openliberty-runtime:20.0.0.3'
    // ...
}
```

In the next step, there needs to be a liberty block to configure the server itself. This following configuration should
be sufficient. Additional configurations can be found here: https://github.com/OpenLiberty/ci.gradle
```groovy
liberty {
    server {
        name = "${appName}Server"
        configDirectory = file("src/main/liberty/config")
        bootstrapProperties = ['default.http.port': testServerHttpPort,
                               'default.https.port': testServerHttpsPort,
                               'app.context.root': warContext]
        packageLiberty {
            packageName = "$buildDir/${appName}.zip"
            include = "usr"
        }
    }
}
```

The last step is the trigger configuration. We need to clarify if the integration tests should be run automatically
after the test stage. Then we could configure it here. This will do some cleaning after running
`gradle integration-test`.
```groovy
clean.dependsOn 'libertyStop'
integrationTest.dependsOn 'libertyStart'
integrationTest.finalizedBy 'libertyStop'
libertyPackage.dependsOn 'libertyStop'

```

## server.xml

We need a server.xml for a minimal deployment configuration. Add it as a file to the project in
`/src/main/liberty/config/server.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<server description="${project.name}">

    <featureManager>
        <feature>javaee-7.0</feature>
    </featureManager>

    <httpEndpoint id="defaultHttpEndpoint"
                  host="*"
                  httpPort="9080"
                  httpsPort="9443"/>

    <webApplication location="gradle-liberty-plugin-for-integration-tests.war" 
                    contextRoot="/gradle-liberty-plugin-for-integration-tests"/>
    <mpMetrics authentication="false"/>
</server>
```

The name of the war archive needs to be adjusted to the relevant filename. Other settings for Hazelcast or databases
may be copied from your local server.xml. You may also want to change the contextRoot.
