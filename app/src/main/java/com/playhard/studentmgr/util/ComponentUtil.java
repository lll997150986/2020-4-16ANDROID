package com.playhard.studentmgr.util;

import android.widget.EditText;
import android.widget.TextView;

import com.playhard.studentmgr.activity.ActivityConfig;

/**
 * Created by user on 2020/4/16.
 */

public class ComponentUtil {
    public static synchronized void changeTVFontSize( TextView... views){
        int i=0;
            for (TextView view: views){
                float size = view.getTextSize();
                view.setTextSize( ActivityConfig.DEFAULT_FONT_SIZE_TIMES*size);
            }
    }

    public static  synchronized void changeETFontSize( EditText... views){
        int i=0;
        for (EditText view: views){
            float size = view.getTextSize();
            view.setTextSize(ActivityConfig.DEFAULT_FONT_SIZE_TIMES * size);
        }
    }
}
