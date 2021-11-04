package io.github.artenes.speedbro.speedrun.com;

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

    private static String padTime(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

}
