package com.yaozu.mvp.presenter.main;


import com.yaozu.mvp.presenter.BasePresenter;

/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/5
 * @Desc:
 */

public class MainPresenter extends BasePresenter<IMainView> {


    public MainPresenter(boolean openEventBus){
        super(openEventBus);
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }

    /**
     * 如果页面有网络请求，同时业务需要在页面销毁的时候取消网络请求
     * 则需要重载该方法
     * @param url
     */
    @Override
    public void setRequestTag(String url) {
        super.setRequestTag(url);
    }

}
