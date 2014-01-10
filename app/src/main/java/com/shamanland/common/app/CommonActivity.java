package com.shamanland.common.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.StartActivityForResult;
import android.support.v4.app.SupportV4App;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;

public class CommonActivity extends ActionBarActivity {
    private static final String LOG_TAG = CommonActivity.class.getSimpleName();

    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        if (requestCode == -1) {
            StartActivityForResult.invoke(this, intent, -1);
            return;
        }

        if ((requestCode & 0xffff0000) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }

        // 16 bit available, 3 bits by one digit
        int chain = 0;
        // max depth is 16 / 3 = 5
        int depth = 0;

        Fragment node = fragment;

        do {
            if (depth > 5) {
                throw new IllegalStateException("Too deep structure of fragments (max 5)");
            }

            int index = SupportV4App.fragmentIndex(node);
            if (index < 0) {
                throw new IllegalStateException("Fragment is out of FragmentManager: " + node);
            }

            // available index is 0..6 due to +1 obligation
            if (index > 6) {
                throw new IllegalStateException("Too many fragments inside (max 7): " + node.getParentFragment());
            }

            // we have to add +1 because in other case we can't determine 0 index
            chain = (chain << 3) + (index + 1);
            node = node.getParentFragment();
            depth += 1;
        } while (node != null);

        int newCode = (chain << 16) + (requestCode & 0xffff);

        StartActivityForResult.invoke(this, intent, newCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ignore super-call

        SupportV4App.activityFragmentsNoteStateNotSaved(this);

        int chain = requestCode >> 16;
        if (chain != 0) {
            ArrayList<Fragment> active = SupportV4App.activityFragmentsActive(this);
            Fragment fragment;

            do {
                int index = (chain & 7) - 1;
                if (active == null || index < 0 || index >= active.size()) {
                    Log.w(LOG_TAG, "Activity result fragment chain out of range: 0x" + Integer.toHexString(requestCode));
                    return;
                }

                fragment = active.get(index);
                if (fragment == null) {
                    break;
                }

                active = SupportV4App.fragmentChildFragmentManagerActive(fragment);
                chain = chain >>> 3;
            } while (chain != 0);

            if (fragment != null) {
                fragment.onActivityResult(requestCode & 0xffff, resultCode, data);
            } else {
                Log.w(LOG_TAG, "Activity result no fragment exists for chain: 0x" + Integer.toHexString(requestCode));
            }
        }
    }
}
