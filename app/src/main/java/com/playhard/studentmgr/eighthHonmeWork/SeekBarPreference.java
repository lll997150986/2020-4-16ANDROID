package com.playhard.studentmgr.eighthHonmeWork;

import android.app.Dialog;
import android.content.Context;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.playhard.studentmgr.R;

/**
 * Created by user on 2020/4/17.
 */

public class SeekBarPreference extends Preference  {
    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }






    public SeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarPreference(Context context) {
        super(context);
    }



    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

    }

    @Override
    protected void onClick() {
        super.onClick();
//        AlertDialog.Builder builder= new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
//       builder.setView(R.layout.myseekbar);
//       builder.setTitle("设置字体大小");
//        AlertDialog dialog = builder.create();
//
//        dialog.show();
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.width = 800;
//        layoutParams.height = 360;
//        dialog.getWindow().setAttributes(layoutParams);
    }
}
