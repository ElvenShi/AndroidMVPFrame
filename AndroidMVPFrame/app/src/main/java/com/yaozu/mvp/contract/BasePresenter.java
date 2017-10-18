package com.yaozu.mvp.contract;

import android.content.Context;

import com.yaozu.base.library.okhttp.OkHttpUtils;
import com.yaozu.base.library.okhttp.request.RequestCall;
import com.yaozu.base.library.utils.StringUtils;
import com.yaozu.mvp.MyApplication;
import com.yaozu.mvp.eventbus.BaseEvent;
import com.yaozu.mvp.eventbus.EventBusConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author shiyaozu
 * @version [版本号, 2017/10/18 0018]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {
    /**
     * Context 实例
     */
    protected Context mAppContext;

    protected V mvpView;
    /**
     * EventBus初始化
     */
    private EventBusConfig mEventConf;

    /**
     * 是否开启eventBus
     */
    private boolean openEventBus;
    /**
     * 网络请求url，用于在activity销毁的时候取消网络请求。
     * 如果业务需要在activity销毁的时候停止网络请求，则只需要调用该方法，记录url
     */
    public String http_request_url;

    public BasePresenter() {
        mAppContext = MyApplication.getContext();
    }

    /**
     * @param openEventBus 是否开启eventBus
     */
    public BasePresenter(boolean openEventBus) {
        this.openEventBus = openEventBus;
        mAppContext = MyApplication.getContext();
    }

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
        if (openEventBus && null != mEventConf) {
            mEventConf.disable();
        }
        try {
            if (StringUtils.isNotNullOrEmpty(getRequestUrl())) {
                RequestCall call = OkHttpUtils.get().url(getRequestUrl()).build();
                call.cancel();
            }
        } catch (Exception e) {

        }
    }

    /**
     * event bus 事件接收处理方法，如果presenter中注册了event bus
     * 则复写或者重载该方法即可
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {

    }

    @Override
    public void setRequestUrl(String url) {
        this.http_request_url = url;
    }

    @Override
    public String getRequestUrl() {
        return http_request_url;
    }
}
