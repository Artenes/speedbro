Feature: Runner details

  Scenario: Browse a runner’s profile
    Given RunnerActivity is opened with a runner ID
    Then the runner image, name and country are displayed
    And the runner’s runs are listed
