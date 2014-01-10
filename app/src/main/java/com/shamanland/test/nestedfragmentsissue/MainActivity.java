package com.shamanland.test.nestedfragmentsissue;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.shamanland.common.app.CommonActivity;

public class MainActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.a_main);

        if (state == null) {
            Bundle args1 = new Bundle();
            args1.putInt("background.color", 0xffff6699);
            args1.putInt("request.code", 999);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_1, Fragment.instantiate(this, TextViewFragment.class.getName(), args1))
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_2, Fragment.instantiate(this, ViewPagerFragment.class.getName()))
                    .commit();
        }
    }
}
