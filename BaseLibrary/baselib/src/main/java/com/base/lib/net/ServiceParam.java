package com.base.lib.net;

import android.support.v7.app.NotificationCompat;

/**
 * Created by allen on 2016/12/22.
 */

public class ServiceParam {
    private int timeOut;
    private boolean isDebug = true;
    private String baseUrl;


    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static class Builder {
        private int timeOut = 5;
        private boolean isDebug = true;
        private String baseUrl;

        public Builder setConnectTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }
        public Builder setIsDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            if(baseUrl == null) throw new IllegalArgumentException();
            this.baseUrl = baseUrl;
            return this;
        }

        public ServiceParam build() {
            ServiceParam serviceParam = new ServiceParam();
            serviceParam.setBaseUrl(baseUrl);
            serviceParam.setDebug(isDebug);
            serviceParam.setTimeOut(timeOut);
            return serviceParam;
        }

    }
}
