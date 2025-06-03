Feature: Video playback

  Scenario: Play a YouTube run
    Given I open a run whose video is on YouTube
    Then the embedded YouTube player loads the video

  Scenario: Open a Twitch run
    Given I open a run whose video is on Twitch
    Then a message prompts me to watch on Twitch
    When I tap the message
    Then the Twitch video opens in the Twitch app or browser
