package com.battleship.gameengine;

import com.battleship.gameengine.ship.placement.ShipPlacementIntegrationTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberIntegrationTest extends ShipPlacementIntegrationTest {
}