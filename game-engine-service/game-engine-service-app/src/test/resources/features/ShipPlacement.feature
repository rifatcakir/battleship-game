Feature: Ship placement tests
  As a player in a naval battle game,
  I want to be able to place my ships on the game board,
  So that I can strategically position my fleet and prepare for battle.

  Background:
    Given a valid game lobby for user John and Jane

  Scenario: Placing a ship on an empty board
    Given User John login
    When Player place a DESTROYER to "A1, A2"
    Then John has a DESTROYER on "A1, A2"

  Scenario: Preventing placing the same ship twice
    Given User John login
    When Player place a DESTROYER to "A1, A2"
    Then John has a DESTROYER on "A1, A2"
    When Player place a DESTROYER to "A1, A2"
    Then Player received '"Given ship is already placed [DESTROYER]"' with error code 400

  Scenario: Placing multiple ships out of turn
    Given User John login
    When Player place a DESTROYER to "A1, A2"
    Then John has a DESTROYER on "A1, A2"
    When Player place a SUBMARINE to "B1, B2, B3"
    Then John has a SUBMARINE on "B1, B2, B3"
    Given User Jane login
    When Player place a CRUISER to "B1, B2, B3"
    Then Jane has a CRUISER on "B1, B2, B3"

  Scenario: Preventing placing ships after all are placed
    Given User John login
    When Player place all ships
    And Player place a AIRCRAFT_CARRIER to "G1, G2, G3, G4, G5"
    Then Player received '"Ship placement not allowed!"' with error code 400

  Scenario: Validating ship positions as vertical or horizontal
    Given User John login
    When Player place a DESTROYER to "A1, B2"
    Then Player received '"Given ship position is not valid"' with error code 400

  Scenario: If player places all ships, player's board status becomes ONGOING
    Given User John login
    When Player place all ships
    Then John board status is ONGOING

  Scenario: Player boards start with SHIP_PLACEMENT state
    Given User John login
    Then John board status is SHIP_PLACEMENT

  Scenario: Player board and game status updated correctly
    When GameBoard status is SHIP_PLACEMENT
    Given User John login
    When Player place all ships
    Then John board status is ONGOING
    When GameBoard status is SHIP_PLACEMENT
    Given User Jane login
    When Player place all ships
    Then Jane board status is ONGOING
    And GameBoard status is ONGOING