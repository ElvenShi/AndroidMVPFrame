package com.yaozu.mvp.contract.base;

/**
 * @author shiyaozu
 * @version [版本号, 2017/10/18 0018]
 * @see [相关类/方法]
 * @since [V1]
 * @desc <view基础接口>
 */
public interface BaseView {

    /**
     * 提示信息
     * @param msg
     */
    void showMessage(String msg);

    /**
     * 关闭资源操作
     */
    void close();

    /**
     * 显示进度信息，不带进度值
     * @param msg
     */
    void showProgress(String msg);

    /**
     * 显示进度信息，带有进度值
     * @param msg
     * @param progress
     */
    void showProgress(String msg, int progress);

    /**
     * 隐藏进度显示
     */
    void hideProgress();

    /**
     * 加载出错提示
     * @param msg
     * @param content
     */
    void showErrorMessage(String msg,String content);
}
