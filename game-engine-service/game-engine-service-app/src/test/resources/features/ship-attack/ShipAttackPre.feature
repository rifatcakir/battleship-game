@ShipAttack
Feature: Ship Attack Initiate Tests
  As a player in a naval battle game,
  I should not be able to attack my opponent's ships on the game board for some reasons.

  Background:
    Given A valid game lobby for user John and Jane

  Scenario: Attack call with non-existent user
    Given Turn is for Gerard
    When Player attack to A1
    Then Player received '"Player board not exits for [Gerard]"' with error code 400

  Scenario: Attack call during non-ongoing game throws error
    When GameBoard status is SHIP_PLACEMENT
    Given Turn is for John
    When Player attack to A1
    Then Player received '"You are not allowed to attack!"' with error code 400

