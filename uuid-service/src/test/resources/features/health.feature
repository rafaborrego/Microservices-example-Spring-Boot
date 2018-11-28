Feature: The application should provide health endpoints

  Scenario: The health endpoint should return status UP
    When the client calls the health endpoint
    Then the health response status code should be 200
    And the result should be "UP"
