Feature: Favorite runs

  Scenario: Add a run to favorites
    Given I am viewing a run in RunActivity
    When I tap the favorite icon
    Then the run is stored as a favorite

  Scenario: View favorites list
    Given a run has been favorited
    When I switch to the "Favorites" tab
    Then the favorited run appears in the list
