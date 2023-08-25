@ShipAttack
Feature: Ship Attack in Game Tests
  As players in a naval battle game,
  we want to strategically attack our opponent's ships on the game board,
  so that we can weaken their fleet and gain an advantage in battle.

  Background: Both players placed ships
    Given A valid game lobby for user John and Jane
    When Turn is for John
    Then given ships are placed
      | shipType         | positions          |
      | DESTROYER        | A1, A2             |
      | SUBMARINE        | B1, B2, B3         |
      | CRUISER          | C1, C2, C3         |
      | BATTLESHIP       | D1, D2, D3, D4     |
      | AIRCRAFT_CARRIER | E1, E2, E3, E4, E5 |
    When Turn is for Jane
    Then given ships are placed
      | shipType         | positions          |
      | DESTROYER        | E1, E2             |
      | SUBMARINE        | D1, D2, D3         |
      | CRUISER          | C1, C2, C3         |
      | BATTLESHIP       | B1, B2, B3, B4     |
      | AIRCRAFT_CARRIER | A1, A2, A3, A4, A5 |

  Scenario: Successful attack response shows it is HIT
    Given Turn is for John
    When Player attack to B3
    Then Player received a BATTLESHIP HIT

  Scenario: MISS attack response shows it is MISS
    Given Turn is for John
    When Player attack to E3
    Then Player received only a MISS

  Scenario:Both player hit with in the order
    Given Turn is for John
    When Player attack to E3
    Then Player received only a MISS
    Given Turn is for Jane
    When Player attack to E3
    Then Player received a AIRCRAFT_CARRIER HIT

  Scenario: Players keeps note if action is hit
    Given Turn is for John
    When Player attack to B3
    Then Player received a BATTLESHIP HIT
    And Player see action result on B3 as HIT

  Scenario: Players keeps note if action is miss
    Given Turn is for John
    When Player attack to E3
    Then Player received only a MISS
    And Player see action result on E3 as MISS

  Scenario: if player hits all pieces of a ship, player see SUNK
    Given Turn is for John
    When Player attack to E1
    Then Player received a DESTROYER HIT
    Given Turn is for Jane
    When Player attack to E3
    Then Player received a AIRCRAFT_CARRIER HIT
    Given Turn is for John
    When Player attack to E2
    Then Player received a DESTROYER SUNK

  Scenario: if a player sunk all ships, win the game
    Given John sunk all ships except DESTROYER for Jane
    Given Turn is for John
    When Player attack to E2
    Then Player received a DESTROYER HIT
    Given Turn is for Jane
    When Player attack to E3
    Then Player received a AIRCRAFT_CARRIER HIT
    Given Turn is for John
    When Player attack to E1
    Then Player received a DESTROYER SUNK
    And John wins the game