Feature: Map of latest runs

  Scenario: Open a cluster of runs
    Given I am viewing the map of runs
    When I tap a country marker
    Then a dialog lists the runs for that country
    And selecting a run opens RunActivity
