package com.base.lib.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
//import android.support.multidex.MultiDex;


/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }


}
