package com.src.fookiiapp.model;

import com.src.fookiiapp.model.entity.BaseEntity;
import com.src.fookiiapp.model.entity.CityEntity;
import com.src.fookiiapp.model.entity.WelcomeImgEntity;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ping on 2017/4/17 0017.
 */

public interface ApiService {
    @GET("/v1/system/launch_img?")
    Observable<BaseEntity<WelcomeImgEntity>> getLaunchImg(@Query("appid") String appId, @Query("type") String type);

    @GET("/v2/house/city_list?")
    Observable<BaseEntity<ArrayList<CityEntity>>> getCityList(@Query("appid") String appid);
}
