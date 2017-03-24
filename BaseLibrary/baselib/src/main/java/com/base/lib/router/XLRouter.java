package com.base.lib.router;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by allen on 2016/12/7.
 */

public class XLRouter {
    private final static String TAG = XLRouter.class.getSimpleName();


    public static<T> T create(final Context context, Class<T> aClass) {
        return (T) Proxy.newProxyInstance(aClass.getClassLoader(), new Class<?>[]{aClass},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                        StringBuilder stringBuilder = new StringBuilder();
                        RouterUri reqUrl = method.getAnnotation(RouterUri.class);
                        Log.e(TAG, "IReqApi---reqUrl->" + reqUrl.routerUri());
                        stringBuilder.append(reqUrl.routerUri());
                        //Type[] parameterTypes = method.getGenericParameterTypes();//获取注解参数类型
                        Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();//拿到参数注解
                        //Annotation[] annotation = method.getDeclaredAnnotations();
                        int pos = 0;
                        for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                            Annotation[] annotations = parameterAnnotationsArray[i];
                            if (annotations != null && annotations.length != 0) {
                                if (pos == 0) {
                                    stringBuilder.append("?");
                                } else {
                                    stringBuilder.append("&");
                                }
                                pos++;
                                RouterParam reqParam = (RouterParam) annotations[0];
                                stringBuilder.append(reqParam.value());
                                stringBuilder.append("=");
                                stringBuilder.append(args[i]);
                                Log.e(TAG, "reqParam---reqParam->" + reqParam.value() + "=" + args[i]);
                            }
                        }
                        //下面就可以执行相应的跳转操作
                        openRouterUri(context, stringBuilder.toString());
                        return null;
                    }
                });
    }

    /**
     * 通过uri跳转指定页面
     *
     * @param context
     * @param url
     */
    private static void openRouterUri(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            context.startActivity(intent);
        }
    }
}
