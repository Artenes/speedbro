Feature: Browse latest runs

  Scenario: View latest runs when opening the app
    Given the application is launched
    Then the tab bar shows "Latest runs" and "Favorites"
    And a list of runs is displayed

  Scenario: Open run details from latest runs
    Given I am on the "Latest runs" tab
    When I tap a run entry
    Then RunActivity opens displaying that run

  Scenario: Switch to map view
    Given I am on the "Latest runs" tab
    When I tap the floating "swap" button
    Then the list is replaced by a map of run locations
