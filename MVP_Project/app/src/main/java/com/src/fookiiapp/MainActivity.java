package com.src.fookiiapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lib.common.net.ApiException;
import com.lib.common.router.LiteRouter;
import com.lib.common.tool.ALog;
import com.lib.common.tool.ListUtils;
import com.lib.mvp.base.BaseActivity;
import com.lib.photopicker.ImageLoader;
import com.lib.photopicker.ImgSelActivity;
import com.lib.photopicker.ImgSelConfig;
import com.lib.widget.toast.UIToast;
import com.src.fookiiapp.contract.MainContract;
import com.src.fookiiapp.databinding.ActivityMainBinding;
import com.src.fookiiapp.model.ApiModel;
import com.src.fookiiapp.model.MainModelImpl;
import com.src.fookiiapp.model.entity.CityEntity;
import com.src.fookiiapp.presenter.MainPresenterImpl;
import com.src.fookiiapp.util.net.RxSubscriber;
import com.ziizaa.main.MainSerVice;

import java.util.ArrayList;

import rx.subscriptions.CompositeSubscription;


public class MainActivity extends BaseActivity<MainPresenterImpl, MainModelImpl, ActivityMainBinding> implements MainContract.View {

    private static final String TAG = "MainActivity";
    CompositeSubscription compositeSubscription;
    ApiModel apiModel;

    @Override
    protected ActivityMainBinding createDataBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        dataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentService intentService = new LiteRouter.Builder().build().create(IntentService.class, getApplicationContext());
//                intentService.stepToLoginActivity("123", "455");
//                selectPhoto();
                MainSerVice mainSerVice = new LiteRouter.Builder().build().create(MainSerVice.class, getApplicationContext());
                mainSerVice.stepToWheelDemo();
            }
        });
        compositeSubscription = new CompositeSubscription();
        apiModel = new ApiModel();
//        getLaunchImg();
        CityEntity cityEntity = new CityEntity();
        cityEntity.city_id = "123";
        cityEntity.city_name = "东莞";
        dataBinding.setCityEntity(cityEntity);
        getCityList();
    }

    private void selectPhoto() {
        ImgSelConfig imgSelConfig = new ImgSelConfig.Builder(imageLoader)
                .multiSelect(false)
                .needCamera(true)
                .build();
        ImgSelActivity.startActivity(this, imgSelConfig, 100);
    }

    private ImageLoader imageLoader = new ImageLoader() {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            com.lib.common.imageloader.ImageLoader.display(context, imageView, path, 0, 0);
        }
    };

    public void getCityList() {
        compositeSubscription.add(apiModel.getCityList().subscribe(new RxSubscriber<ArrayList<CityEntity>>() {
            @Override
            protected void onSuccess(ArrayList<CityEntity> cityEntities) {
                ALog.e(ListUtils.getSize(cityEntities));
                ArrayList<String> cityList = new ArrayList<String>();
                for (int i = 0; i < ListUtils.getSize(cityEntities); i++) {
                    cityList.add(cityEntities.get(i).city_name);
                }
                dataBinding.loopView.setItems(cityList);
            }

            @Override
            protected void onFailed(ApiException apiException) {
                ALog.e(apiException.code + ":" + apiException.message);
            }
        }));
    }

    public void getLaunchImg() {
        compositeSubscription.add(apiModel.getLaunchImg().subscribe());
    }
}
