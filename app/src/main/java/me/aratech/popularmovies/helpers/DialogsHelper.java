package me.aratech.popularmovies.helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

/***
 * Helper for creating Dialogs, Progress Dialogs and Toasts
 */
public class DialogsHelper {

    /***
     * Display toast with custom resource message
     * @param activity Activity to display the toast on it
     * @param messageResourceId Message Resource Id
     */
    public static void showToast(@NonNull Activity activity, int messageResourceId) {
        Toast
                .makeText(activity, messageResourceId, Toast.LENGTH_LONG)
                .show();
    }
}
