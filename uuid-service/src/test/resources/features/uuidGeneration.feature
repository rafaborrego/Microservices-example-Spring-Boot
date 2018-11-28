Feature: The application should generate UUIDs

  Scenario: The UUID should have the expected prefix and suffix
    When the client calls the UUID generation endpoint
    Then the uuid response status code should be 200
    And the UUID should start by "Test_prefix-"
    And the UUID should end by "-text_suffix"

  Scenario: The UUID should be different every time
    Given the client has retrieved a UUID before
    When the client calls the UUID generation endpoint
    Then the uuid response status code should be 200
    And the UUID should be different to the one generated before
