package com.base.lib.tools;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by Allen on 2016/12/20.
 */
public class IntentUtil {

    public static void startActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("steptoviewpager", 0);
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }
}
