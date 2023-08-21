@Listen
Feature:
  This feature focuses on game listening.
  The scenario demonstrates Jane's interaction with the game.

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

  Scenario: Jane can listen and see when she can play
    Given Turn is for Jane
    When Player see turn for PLAYER1
    Given Turn is for John
    Then Player attack to B3
    And Turn is for Jane
    When Player see turn for PLAYER2