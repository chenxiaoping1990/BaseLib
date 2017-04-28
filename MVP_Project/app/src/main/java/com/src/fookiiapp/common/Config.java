package com.src.fookiiapp.common;

/**
 * Created by Ping on 2017/4/17 0017.
 */

public class Config {
    public static final boolean IS_DEBUG = true;
    public static final String APPID = "fb3d25c4a4c14bc23157678d1dacb738";

    public static String getHost() {
        if (IS_DEBUG) {
            return Api.DEV_HOST;
        } else {
            return Api.PRO_HOST;
        }
    }
}
