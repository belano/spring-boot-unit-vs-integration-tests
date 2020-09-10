@integration # Cucumber tags are mapped to JUnit tags
Feature: Greeting functionalities

  Scenario: Greeting jdoe
    Given the greeting counter is reset
    When I greet jdoe
    Then the greeting contains 'Hello, jdoe!'