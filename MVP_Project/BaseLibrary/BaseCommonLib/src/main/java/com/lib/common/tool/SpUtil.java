package com.lib.common.tool;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.lib.common.BaseApplication;

/**
 * Preference操作类
 *
 * @author Ping
 */
public class SpUtil {

    private static SharedPreferences mPerferences = PreferenceManager
            .getDefaultSharedPreferences(BaseApplication.getContext());

    /**
     * 设置字符数据
     *
     * @param key
     * @param value
     */
    public static boolean putString(String key, String value) {
        Editor editor = mPerferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putInt(String key, int value) {
        Editor editor = mPerferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean putLong(String key, long value) {
        Editor editor = mPerferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putBoolean(String key, Boolean value) {
        Editor editor = mPerferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean putFloat(String key, float value) {
        Editor editor = mPerferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 获取字符属性值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return mPerferences.getString(key, "");
    }

    public static int getInt(String key) {
        return mPerferences.getInt(key, -1);
    }

    public static Boolean getBoolean(String key) {
        return mPerferences.getBoolean(key, false);
    }

    public static boolean getBooleanForDefaultTure(String key) {
        return mPerferences.getBoolean(key, true);
    }

    public static long getLong(String key) {
        return mPerferences.getLong(key, 0);
    }

    public static float getFloat(String key) {
        return mPerferences.getFloat(key, 0);
    }


    public static void clearAll() {
        Editor editor = mPerferences.edit();
        editor.clear();
        editor.commit();
    }

}
