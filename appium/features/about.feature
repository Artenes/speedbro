Feature: About section

  Scenario: Access project links
    Given I open the About screen
    Then the application version is visible
    When I tap "My website"
    Then the external browser opens the developer’s website
    When I tap "Git repository"
    Then the external browser opens the project repository
