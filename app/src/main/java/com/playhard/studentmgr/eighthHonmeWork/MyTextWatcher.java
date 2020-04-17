package com.playhard.studentmgr.eighthHonmeWork;

import android.content.Context;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by user on 2020/4/16.
 */

    class MyTextWatcher implements TextWatcher {

        private Context context;
        private Preference preference;

        public MyTextWatcher(Context context, Preference preference) {
            this.context = context;
            this.preference = preference;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            preference.setSummary(editable);
        }
    }
