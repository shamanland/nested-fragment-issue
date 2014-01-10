package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;

public class StartActivityForResult extends Activity {
    public static void invoke(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }
}
