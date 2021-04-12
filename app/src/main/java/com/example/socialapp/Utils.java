package com.example.socialapp;

import android.text.format.DateUtils;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

public class Utils {

    /**
     * converts time (in milliseconds) to human-readable format
     *  "<w> days, <x> hours, <y> minutes and (z) seconds"
     */
    public static String getTimeAgo(long duration) {
        String finalTime= (String) DateUtils.getRelativeTimeSpanString(duration,System.currentTimeMillis(),MINUTE_IN_MILLIS);
        return finalTime;
    }
}
