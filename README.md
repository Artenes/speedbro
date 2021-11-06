# Speed Bro - Unofficial Speedrun.com browser

![Cover image](images/banner.png)

[![Build Status](https://www.travis-ci.org/Artenes/speedbro.svg?branch=master)](https://www.travis-ci.org/Artenes/speedbro)

<a href='https://play.google.com/store/apps/details?id=io.github.artenes.speedbro&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="200px" /></a>

Browsing application that allows the user to navigate through games, speed runs and users available at [https://www.speedrun.com](https://www.speedrun.com).

# Demo

<iframe width="560" height="315" src="https://www.youtube.com/embed/lhVu-wm54So" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

# Features

- Browse through the latest runs
- Favorite runs
- See the latest runs in a map view
- Browse the runs of a game
- Browse the runs of a runner
- Whatch the runs directly on the app (if the run is hosted on youtube)
- Search for games

# Notes

- The apps makes use of speedrun.com API
- It plays videos hosted on Youtube using the Youtube API. If the video is hosted on Twitch the Twitch app is opened to play the video.
- It uses the Google Maps library to display runs around the world
- It is configured to run on [travis-ci.org](https://travis-ci.org/) 

# Screenshots

<p float="left" align="center">
  <img src="images/speed01.png" width="300px" />
  <img src="images/speed03.png" width="300px" /> 
</p>

<p float="left" align="center">
  <img src="images/speed04.png" width="300px" />
  <img src="images/speed05.png" width="300px" /> 
</p>

# Set up

Requirements

- Have an API key for Youtube ([How to get a Google Maps API key](https://developers.google.com/maps/documentation/android-sdk/signup))
- Have an API key for Google Maps ([How to get a Youtube API key](https://developers.google.com/youtube/android/player/register))

1. Add the following lines to the gradle.properties in your user directory (``~/.gradle`` for linux or ``C:\Users\{you}\.gradle`` for Windows). If neither the file or directory exists, just create it.

````
SpeedBro_YoutubeAPIKey={your-youtube-api-key}
SpeddBro_MapsAPIKey={your-maps-api-key}
````

Replace ``{your-youtube-api-key}`` with your Youtube API key and ``{your-maps-api-key}`` with your Google Maps API key.

2. Clone the project
```
git@github.com:Artenes/speedbro.git
```

3. Import project in Android Studio

4. Run the app on a device or emulator

# License

Creative Commons Attribution-NonCommercial 4.0 International Public License