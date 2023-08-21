package com.battleship.gameengine.cucumber.listen;

import com.battleship.gameengine.GameEngineApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
        tags = "@Listen", dryRun = true,
        glue = {"com.battleship.gameengine.cucumber.common",
                "com.battleship.gameengine.cucumber.listen"})
@CucumberContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = GameEngineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameLobbyListenTest {
}
