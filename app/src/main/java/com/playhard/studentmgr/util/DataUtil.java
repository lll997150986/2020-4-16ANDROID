package com.playhard.studentmgr.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2020/4/16.
 */

public class DataUtil {
    public static  void  dataRecovery(Context context, String path, int Mode, String[] key, TextView... views){
        int i= 0;
        String data="";
        while (i<key.length){
            data = context.getSharedPreferences(path,Mode).getString(key[i],"");
            if (data!=""){
                views[i++].setText(data);
            }

        }

    }
}
