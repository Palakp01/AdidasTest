Feature: Get List of Available Pets

  Scenario: Get List of available pets
    When user request to search list of "available" pets
    Then list of "available" pets returned in response