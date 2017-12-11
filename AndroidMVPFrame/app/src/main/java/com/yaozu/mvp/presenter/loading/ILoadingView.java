package com.yaozu.mvp.presenter.loading;


import com.yaozu.mvp.presenter.IMvpView;

/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/5
 * @Desc:
 */

public interface ILoadingView extends IMvpView {
    //直接跳转至引导页
    void skipToGuide();

    //跳转至当前版本引导页
    void skipToCurrentGuide(int guideVersion);

    //已登录，跳转至首页
    void skipToMain();

    //未登录，跳转至登录页
    void skipToLogin();
}
