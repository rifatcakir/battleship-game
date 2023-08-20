package com.battleship.authservice.actuator;

import com.battleship.authservice.AuthServiceApplication;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
        classes = {AuthServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class SpringActuatorFeature {

  @LocalServerPort
  private String localServerPort;

  @BeforeEach
  public void init() {
  }

  @Karate.Test
  public Karate actuatorResourceIsAvailable() {
    return karateScenario("actuator resource is available");
  }

  @Karate.Test
  public Karate healthResourceStatusIsUp() {
    return karateScenario("health resource status is \"up\"");
  }

  private Karate karateScenario(String s) {
    return Karate.run("classpath:/features")
            .scenarioName(s)
            .relativeTo(getClass())
            .systemProperty("karate.port", localServerPort)
            .karateEnv("dev");
  }
}
