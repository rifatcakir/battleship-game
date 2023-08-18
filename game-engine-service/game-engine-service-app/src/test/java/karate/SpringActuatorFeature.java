package karate;

import com.battleship.gameengine.GameEngineApplication;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = {GameEngineApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpringActuatorFeature {

  @LocalServerPort
  private String localServerPort;

  @BeforeEach
  public void init() {
  }

  @Karate.Test
  public Karate actuatorResourceIsAvailable() {
    return karateSzenario("actuator resource is available");
  }

  @Karate.Test
  public Karate healthResourceStatusIsUp() {
    return karateSzenario("health resource status is \"up\"");
  }

  @Karate.Test
  public Karate asdasd() {
    return karateSzenario("h resource status");
  }

  private Karate karateSzenario(String s) {
    return Karate.run()
            .scenarioName(s)
            .relativeTo(getClass())
            .systemProperty("karate.port", localServerPort)
            .karateEnv("dev");
  }
}
