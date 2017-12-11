package com.yaozu.mvp.presenter;

/**
 * <基础业务类>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
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
    void setRequestTag(String url);

    /**
     * 提取request url
     * @return
     */
    String getRequestUrl();
}
