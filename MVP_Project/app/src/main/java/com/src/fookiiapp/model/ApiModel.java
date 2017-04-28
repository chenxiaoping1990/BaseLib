package com.src.fookiiapp.model;

import com.lib.common.net.ApiException;
import com.lib.common.net.RetrofitUtil;
import com.lib.common.tool.ALog;
import com.src.fookiiapp.common.Config;
import com.src.fookiiapp.model.entity.BaseEntity;
import com.src.fookiiapp.model.entity.CityEntity;
import com.src.fookiiapp.model.entity.WelcomeImgEntity;
import com.src.fookiiapp.util.net.RxSchedulers;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Ping on 2017/4/17 0017.
 */

public class ApiModel {

    private ApiService apiService;

    public ApiModel() {
        apiService = RetrofitUtil.createService(Config.getHost(), ApiService.class);
    }

    public Observable<BaseEntity<WelcomeImgEntity>> getLaunchImg() {
        return apiService.getLaunchImg(Config.APPID, "android")
                .compose(RxSchedulers.<BaseEntity<WelcomeImgEntity>>io_main());
    }

    public Observable<BaseEntity<ArrayList<CityEntity>>> getCityList() {
        return apiService.getCityList(Config.APPID).compose(RxSchedulers.<BaseEntity<ArrayList<CityEntity>>>io_main());
    }
}
