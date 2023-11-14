Feature: Sample tests

  Scenario: Mocked first integration test
    Given we prepare data -mocked
    When data is mutated -mocked
    Then the data is changed -mocked