package me.aratech.popularmovies.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/***
 * Helper for Intent common tasks
 * Like open Url
 */
public class IntentHelper {

    /***
     * Open Default Browser app with Url
     * @param url Url string to to open
     * @param context Context to be able to start activity
     */
    public static void openUrl(@NonNull String url, @NonNull Context context) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (myIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(myIntent);
        }
    }

}
