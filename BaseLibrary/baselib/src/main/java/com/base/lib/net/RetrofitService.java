package com.base.lib.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.base.lib.net.converter.FastJsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by allen on 2016/12/22.
 */

public class RetrofitService {
    public static Retrofit get(ServiceParam serviceParam) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(serviceParam.getTimeOut(), TimeUnit.SECONDS);
        String baseUrl = serviceParam.getBaseUrl();
        if (serviceParam.isDebug()) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
            okHttpBuilder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("OkHttp", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截
        }
        OkHttpClient okHttpClient = okHttpBuilder.build();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder.build();
    }


    public static <T> T getModuleApi(ServiceParam serviceParam, Class<T> clazz)  {
        return get(serviceParam).create(clazz);
    }
}
