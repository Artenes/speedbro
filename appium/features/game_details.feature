Feature: Game details

  Scenario: Navigate to a game
    Given a game ID is provided
    When GameActivity is opened
    Then the game cover, title, year and platforms are displayed
    And tabs for each category appear

  Scenario: View a category leaderboard
    Given I am on a game's detail screen
    When I select a category tab
    Then a leaderboard of runs is shown
    When I tap a run on the leaderboard
    Then RunActivity opens for that run
