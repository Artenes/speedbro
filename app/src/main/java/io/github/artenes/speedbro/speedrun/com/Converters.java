package io.github.artenes.speedbro.speedrun.com;

import java.util.List;

import io.github.artenes.speedbro.speedrun.com.api.models.Data__2;

public class Converters {

    public static String toReadableTime(String secondsAndMillis) {
        if (secondsAndMillis == null || secondsAndMillis.isEmpty()) {
            return "00:00";
        }

        int timeInSeconds = Integer.parseInt(secondsAndMillis.split("\\.")[0]);
        int hours = timeInSeconds / 60 / 60;
        int minutes = timeInSeconds / 60 % 60;
        int seconds = timeInSeconds % 60;

        return padTime(hours) + ":" + padTime(minutes) + ":" + padTime(seconds);
    }

    public static String toSimplePlatformList(List<Data__2> platformsList) {

        StringBuilder platforms = new StringBuilder();

        if (platformsList == null || platformsList.isEmpty()) {
            return platforms.toString();
        }

        for (Data__2 platform : platformsList) {
            platforms.append(platform.name).append(",");
        }

        return platforms.substring(0, platforms.length() - 1);

    }

    private static String padTime(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

}
