Feature: Latest runs widget

  Scenario: Open run from widget
    Given the widget shows a list of recent runs
    When I tap a run on the widget
    Then the application opens RunActivity for that run
