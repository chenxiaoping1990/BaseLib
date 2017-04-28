package com.src.fookiiapp.util.net;

import android.app.Activity;
import android.content.Context;

import com.lib.common.BaseApplication;
import com.lib.common.net.ApiException;
import com.lib.common.net.NetError;
import com.lib.common.tool.ALog;
import com.lib.common.tool.NetUtil;
import com.src.fookiiapp.model.entity.BaseEntity;

import rx.Subscriber;

/**
 * des:订阅封装
 * on 2016.09.10:16
 */

/********************
 * 使用例子
 ********************/
public abstract class RxSubscriber<T> extends Subscriber<BaseEntity<T>> {


    @Override
    public void onCompleted() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onNext(BaseEntity<T> baseEntity) {
        if (baseEntity == null) {
            onFailed(new ApiException(NetError.JSON_FAILED, "数据异常"));
            return;
        }
        if (baseEntity.ret != 0) {
            onFailed(new ApiException(baseEntity.ret, baseEntity.msg));
            return;
        }
        ALog.e(baseEntity.data.toString());
        onSuccess(baseEntity.data);
    }

    @Override
    public void onError(Throwable e) {
        //网络
        if (!NetUtil.isConnectInternet(BaseApplication.getContext())) {
            onFailed(new ApiException(NetError.NO_NET, "网络断开,请检查后重新尝试"));
        } else {
            onFailed(new ApiException(NetError.NET_ERROR, "网络异常"));
        }
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailed(ApiException apiException);

}
