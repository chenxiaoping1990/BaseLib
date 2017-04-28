package com.src.fookiiapp.contract;

import com.lib.mvp.base.BaseModel;
import com.lib.mvp.base.BasePresenter;
import com.lib.mvp.base.BaseView;

/**
 * Created by Ping on 2017/4/21 0021.
 */

public interface MainContract {
    interface View extends BaseView {
    }

    class Presenter extends BasePresenter<View, Model> {
    }

    interface Model extends BaseModel {

    }
}