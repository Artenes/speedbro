package io.github.artenes.speedbro.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

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
     * Start an Intent to an action view
     *
     * @param uri     the uri to view
     * @param context the current context
     */
    public static void startViewIntent(String uri, Context context) {
        Uri uriContent = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriContent);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Check if the given package is installed
     *
     * @param packagename the name of the package to check
     * @param context     the context of the app
     * @return either if the app is installed or not
     */
    private static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}