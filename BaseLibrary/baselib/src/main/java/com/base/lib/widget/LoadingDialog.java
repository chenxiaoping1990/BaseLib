package com.base.lib.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

/**
 * Created by vam on 2016/10/12.
 */

public class LoadingDialog {
    private static Dialog loadingDialog;

    /**
     * 显示无标题的滚动条
     *
     * @param activity
     * @param message
     */
    public static void showLoadingDialog(Activity activity, String message) {
        showLoadingDialog(activity, "", message);
    }

    /**
     * 显示滚动条
     *
     * @param activity
     * @param message
     */
    public static void showLoadingDialog(Activity activity, String title, String message) {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog.show(activity, title, message);
        }
    }

    /**
     * 取消滚动框的显示
     */
    public static void cancel() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }
}
