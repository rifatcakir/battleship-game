Feature: Actuator api

  Scenario: actuator resource is available
    Given url baseUrl + '/actuator'
    When method get
    Then status 200

  Scenario: health resource status is "up"
    Given url baseUrl + '/actuator/health'
    When method get
    Then status 200
    And match response == {'status':'UP'}

  Scenario: h resource status
    Given url  baseUrl + '/game-engine/v0/actions/test'
    When method get
    Then status 200