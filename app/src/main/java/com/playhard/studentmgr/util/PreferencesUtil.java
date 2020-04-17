package com.playhard.studentmgr.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;

/**
 * Created by user on 2020/3/25.
 */

public class PreferencesUtil {

    public static void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public static void clear(Context context,String xmltag) {
        SharedPreferences preferences = context.getSharedPreferences(xmltag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
