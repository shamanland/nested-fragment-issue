package android.support.v4.app;

import java.util.ArrayList;

public class SupportV4App {
    public static ArrayList<Fragment> activityFragmentsActive(FragmentActivity activity) {
        return activity.mFragments.mActive;
    }

    public static int fragmentIndex(Fragment fragment) {
        return fragment.mIndex;
    }

    public static ArrayList<Fragment> fragmentChildFragmentManagerActive(Fragment fragment) {
        return ((FragmentManagerImpl) fragment.getChildFragmentManager()).mActive;
    }
}
