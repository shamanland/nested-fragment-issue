package com.shamanland.common.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.StartActivityForResult;
import android.support.v4.app.SupportV4App;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class CommonActivity extends ActionBarActivity {
    private static final String LOG_TAG = CommonActivity.class.getSimpleName();

    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        Log.v(LOG_TAG, "startActivityFromFragment");

        if (requestCode == -1) {
            StartActivityForResult.invoke(this, intent, -1);
            return;
        }

        if ((requestCode & 0xffff0000) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }

        int newCode = ((SupportV4App.fragmentIndex(fragment) + 1) << 16) + (requestCode & 0xffff);

        StartActivityForResult.invoke(this, intent, newCode);
    }
}
