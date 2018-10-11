# Speed Bro - Unofficial Speedrun.com browser

Browsing application that allows the user to navigate through games, speed runs and users available at [https://www.speedrun.com](https://www.speedrun.com).

# Set up

1. Have an API key for Youtube and Google Maps.
    - [How to get a Youtube API key](https://developers.google.com/youtube/android/player/register)
    - [How to get a Google Maps API key](https://developers.google.com/maps/documentation/android-sdk/signup)

2. Add the following lines to the gradle.properties in your user directory (~/.gradle for linux or C:\Users\{you}\.gradle). If neither the file or directory exists, just create it.

````
SpeedBro_YoutubeAPIKey="{your-youtube-api-key}"
SpeddBro_MapsAPIKey={your-maps-api-key}
````

Replace ``{your-youtube-api-key}`` with your Youtube API key and ``{your-maps-api-key}`` with your Google Maps API key. Note that the Youtube key is surrounded by quotes, while the Google Maps one is not. 


3. Clone the project
```
git@github.com:Artenes/speedbro.git
```

4. Import project in Android Studio

5. Run the app on a device or emulator

# License

Creative Commons Attribution-NonCommercial 4.0 International Public License