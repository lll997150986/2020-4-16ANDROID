package com.playhard.studentmgr.activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.util.PreferencesUtil;

public class ActivityConfig extends PreferenceActivity  {
    final  String XMLPREFERENCEPATH="com.playhard.studentmgr_preferences";
    SwitchPreference modePreference;
    EditTextPreference namePreference;
    Preference preference;
    private static final String TAG = "ActivityConfig";
    private SeekBar sb ;
    public static volatile float DEFAULT_FONT_SIZE_TIMES=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_config);

        View view = View.inflate(ActivityConfig.this,R.layout.seekbar_comp,null);
        getListView().addFooterView(view);
        getListView().setItemsCanFocus(true);

        namePreference = (EditTextPreference) findPreference("username");
        String name =getSharedPreferences(XMLPREFERENCEPATH,MODE_PRIVATE).getString("username","");;
        namePreference.setSummary(name);
//        namePreference.getEditText().addTextChangedListener(new MyTextWatcher(this,namePreference));


        modePreference = (SwitchPreference) findPreference("v_h_mode");
        modePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (modePreference.isChecked()){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        preference=  findPreference("fontSize");

//        sb =view.findViewById(R.id.fontSizeSeekBar);
        sb = findViewById(R.id.fontSizeSeekBar);
        if (sb!=null){
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                int initSize;
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (i>5){
                        DEFAULT_FONT_SIZE_TIMES = (float) (((i-5)/100.0)+1);
                    }
                    else {
                        DEFAULT_FONT_SIZE_TIMES = (float) (((i-5)/7.0)+1);
                    }


                    preference.setSummary(i+"");
                    Log.d(TAG, "onProgressChanged: DEFAULT_FONT_SIZE_TIMES"+DEFAULT_FONT_SIZE_TIMES);
                    Log.d(TAG, "onProgressChanged: i"+i);
                    initSize = i;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SharedPreferences.Editor edit = getSharedPreferences("fontsize", MODE_PRIVATE).edit();
                    edit.putInt("fontSize",initSize);
                    edit.commit();
                }
            });
        }

        SharedPreferences preferences = getSharedPreferences("fontsize",MODE_PRIVATE);
        int size = preferences.getInt("fontSize",5);
        Log.d(TAG, "onResume: size  "+size);
        sb.setProgress(size);
        PreferencesUtil.clear(ActivityConfig.this,"fontsize");

    }

}
