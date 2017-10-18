package com.yaozu.mvp.contract;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/18 0018
 * @desc : [presenter基础接口]
 */

public interface Presenter<V> {
    /**
     * activity/fragment通过view实现跟presenter的绑定
     *
     * @param view
     */
    void attachView(V view);

    /**
     * activity/fragment通过view实现跟presenter的解绑
     */
    void detachView();

    /**
     * 设置请求url，用于在activity销毁的时候取消正在进行的网络请求
     * @param url
     */
    void setRequestUrl(String url);

    /**
     * 提取request url
     * @return
     */
    String getRequestUrl();
}
