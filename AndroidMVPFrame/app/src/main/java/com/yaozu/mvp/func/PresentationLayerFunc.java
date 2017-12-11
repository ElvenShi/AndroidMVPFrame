package com.yaozu.mvp.func;

import android.view.View;

import com.mobcb.dialog.CustomAlertDialog;


/**
 * <页面基础公共功能抽象>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public interface PresentationLayerFunc {

    /**
     * 弹出Toast
     * @param msg
     */
    void showToast(String msg);

    /**
     * 弹出Toast
     * @param id
     */
    void showToast(int id);


    /**
     * 网络请求加载框
     */
    void showProgressDialog(String msg);

    /**
     * 网络请求加载框
     */
    void showProgressDialog(int id);

    /**
     * 隐藏网络请求加载框
     */
    void hideProgressDialog();

    /**
     * 显示软键盘
     *
     * @param focusView
     */
    void showSoftKeyboard(View focusView);
    /**
     * 隐藏软键盘
     */
    void hideSoftKeyboard(View focusView);
    
    void hideSoftKeyboard();

    /**
     * @param title   标题
     * @param message 内容
     */
    void showAlert(String title, String message);

    /**
     * @param content 内容
     * @param theme   主题
     */
    void showAlert(String content, CustomAlertDialog.DIALOG_THEME theme);

    /**
     *
     * @param content
     * @param theme
     * @param btnText
     */
    void showAlert(String content, CustomAlertDialog.DIALOG_THEME theme, String btnText);

    /**
     * 设置dialog是否可以取消
     * */
    void setAlertDialogCancelable(boolean enable);

    /**
     * 隐藏交互对话框
     */
    void hideAlert();

    /**
     * @return 是否有正在显示的对话框，包含加载动画和alert
     */
    boolean isShowing();

}
