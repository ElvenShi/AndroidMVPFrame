package com.yaozu.mvp.presenter;

import com.yaozu.mvp.contract.HomeContract;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/20 0020
 * @desc : [相关类/方法]
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    public HomePresenter() {
    }

    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void setRequestUrl(String url) {

    }

    @Override
    public String getRequestUrl() {
        return null;
    }

    @Override
    public void initData() {

    }
}
