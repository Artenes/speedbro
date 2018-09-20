package io.github.artenes.speedbro.speedrun.com;

/**
 * Utility class
 */
public class Utils {

    /**
     * Get the last segment of an uri separated by /
     *
     * @param uri the uri to get the segment from
     * @return the last segment of the uri if it is not empty
     */
    public static String lastSegmentOfUri(String uri) {
        if (uri.isEmpty()) {
            return uri;
        }

        String[] segments = uri.split("/");

        if (segments.length == 0) {
            return uri;
        }

        return segments[segments.length - 1];
    }

    /**
     * Get a string without its first character, which in this
     * case is used primarily to remove a forward slash from relative paths
     *
     * @param value the string to remove the slash from
     * @return the stripped string
     */
    public static String withoutStartingSlash(String value) {
        if (value.isEmpty()) {
            return value;
        }
        if (!value.startsWith("/")) {
            return value;
        }
        return value.substring(1, value.length());
    }

    /**
     * Get the first word of a sentence
     *
     * @param sentence the sentence
     * @return the first word if one is available
     */
    public static String getFirstWordOfSentence(String sentence) {
        if (sentence.isEmpty()) {
            return sentence;
        }
        if (!sentence.contains(" ")) {
            return sentence;
        }
        String[] words = sentence.split(" ");
        if (words.length == 0) {
            return sentence;
        }
        return words[0];
    }

    /**
     * Parse the time in the API format (PT34H56M12S - PT=primary time, H=hour, M=minute, S=seconds)
     * @param time the time to parse
     * @return the parsed time (34h56m12s)
     */
    public static String parseTime(String time) {
        if (time.startsWith("PT")) {
            //strip the PT from the beginning and put all letteres in lower case
            return time
                    .toLowerCase()
                    .substring(2, time.length());
        }
        return time;
    }

}