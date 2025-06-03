Feature: Search functionality

  Scenario: Search for a game
    Given I tap the search icon
    When I enter a game name and submit
    Then a list of search results is shown
    When I select a game result
    Then GameActivity opens for that game

  Scenario: Search for a runner
    Given I tap the search icon
    When I enter a runner name and submit
    Then the runner appears in the results
    When I select the runner
    Then RunnerActivity opens for that runner
