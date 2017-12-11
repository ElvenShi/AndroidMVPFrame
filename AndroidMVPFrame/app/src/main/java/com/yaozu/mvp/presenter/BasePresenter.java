package com.yaozu.mvp.presenter;

import android.content.Context;

import com.yaozu.base.library.okhttp.OkHttpUtils;
import com.yaozu.base.library.okhttp.request.RequestCall;
import com.yaozu.base.library.utils.GeneralUtils;
import com.yaozu.mvp.MyApplication;
import com.yaozu.mvp.eventbus.EventBusConfig;
import com.yaozu.mvp.eventbus.event.BaseEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * <基础业务类>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class BasePresenter<V extends IMvpView> implements Presenter<V> {


    /**
     * Context 实例 MTApplication
     *
     * */
    protected Context mAppContext;

    protected V mvpView;

    /**
     * 是否开启eventBus
     */
    private boolean openEventBus;

    public BasePresenter() {
        mAppContext = MyApplication.getContext();
    }

    /**
     * @param eventOpen 是否开启eventBus
     */
    public BasePresenter(boolean eventOpen) {
        this.openEventBus = eventOpen;
        mAppContext = MyApplication.getContext();
    }

    /**
     * EventBus初始化
     */
    private EventBusConfig mEventConf;

    /**
     * 网络请求tag，用于在activity销毁的时候取消网络请求。
     * 如果业务需要在activity销毁的时候停止网络请求，则通过该tag取消请求
     */
    public String requestTag;


    public void attachView(V view) {
        try {
            mvpView = view;
            if (openEventBus) {
                mEventConf = EventBusConfig.getInstance(this);
                mEventConf.enable();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void detachView() {
        mvpView = null;
        if (openEventBus && null != mEventConf){
            mEventConf.disable();
        }

        try {
            if (GeneralUtils.isNotNullOrEmpty(getRequestUrl())){
                RequestCall call = OkHttpUtils.get().url(getRequestUrl()).build();
                call.cancel();
            }

        } catch (Exception e) {

        }
    }

    /**
     * event bus 事件接收处理方法，如果presenter中注册了event bus
     * 则复写或者重载该方法即可
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {

    }

    @Override
    public void setRequestTag(String url) {
        this.requestTag = url;
    }

    @Override
    public String getRequestUrl() {
        return requestTag;
    }

}
