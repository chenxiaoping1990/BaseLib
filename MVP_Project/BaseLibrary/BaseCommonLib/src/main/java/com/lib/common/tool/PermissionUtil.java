package com.lib.common.tool;

import android.Manifest;
import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Ping on 2017/4/6 0006.
 */

public class PermissionUtil {
    public static final int PHOTO_PERMISSION = 1;
    public static final int ALL_PERMISSION = 2;

    /**
     * 相机权限
     *
     * @param context
     * @return
     */
    public static boolean checkCameraPermission(Activity context) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(context, perms)) {
            EasyPermissions.requestPermissions(context, "为了提供更好的服务，需要提供一下权限", PHOTO_PERMISSION, perms);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 蓝牙模块
     *
     * @param activity
     * @return
     */
    public static boolean checkBlePermission(Activity activity) {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!EasyPermissions.hasPermissions(activity, perms)) {
            EasyPermissions.requestPermissions(activity, "为了提供更好的服务，需要提供一下权限", 0, perms);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 应用需要的权限
     *
     * @param activity
     * @return
     */
    public static boolean checkAppPermission(Activity activity) {
        String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR};
        if (!EasyPermissions.hasPermissions(activity, perms)) {
            EasyPermissions.requestPermissions(activity, "为了能够正常使用功能，需要提供一下权限", ALL_PERMISSION, perms);
            return false;
        } else {
            return true;
        }
    }
}
