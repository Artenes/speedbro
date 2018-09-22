package io.github.artenes.speedbro.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Utility class for the application
 */
public class Utils {

    private static final String TWITCH_APP_PACKAGE = "tv.twitch.android.app";

    /**
     * Check if the Twitch app is installed
     *
     * @param context the context of the app
     * @return either if the Twitch app is installed
     */
    public static boolean isTwitchInstalled(Context context) {
        return isPackageInstalled(TWITCH_APP_PACKAGE, context);
    }

    /**
     * Check if the given package is installed
     *
     * @param packagename the name of the package to check
     * @param context     the context of the app
     * @return either if the app is installed or not
     */
    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}