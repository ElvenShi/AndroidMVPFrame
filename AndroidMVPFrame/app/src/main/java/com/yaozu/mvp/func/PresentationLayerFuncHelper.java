package com.yaozu.mvp.func;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mobcb.dialog.CustomAlertDialog;
import com.mobcb.dialog.LoadingDialog;
import com.mobcb.widget.ToastUtil;

/**
 * <页面基础公共功能实现>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public class PresentationLayerFuncHelper implements PresentationLayerFunc {

    private Context context;
    /**
     * 加载中
     */
    protected LoadingDialog mProgressDialog;

    public CustomAlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    /**
     * 提示对话框
     */
    protected CustomAlertDialog mAlertDialog;

    public PresentationLayerFuncHelper(Context context) {
        this.context = context;
    }


    @Override
    public void showToast(String msg) {
        ToastUtil.showCenterToast(context, msg);
    }

    @Override
    public void showToast(int id) {
        ToastUtil.showCenterToast(context, id);
    }


    public void showProgressDialog(String msg) {
        if (null == mProgressDialog) {
            mProgressDialog = new LoadingDialog(context, msg);
        } else {
            mProgressDialog.setLoadingMsg(msg);
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void showProgressDialog(int id) {
        showProgressDialog(context.getString(id));
    }

    @Override
    public void hideProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showSoftKeyboard(View focusView) {

    }

    /**
     * OnClick事件下有效
     *
     * @param focusView
     */
    @Override
    public void hideSoftKeyboard(@NonNull View focusView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != inputMethodManager) {

            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            focusView.clearFocus();
        }
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public void showAlert(String title, String message) {
        if (null == mAlertDialog) {
            mAlertDialog = new CustomAlertDialog(context, title, message);
        } else {
            mAlertDialog.setTitle(title);
            mAlertDialog.setDialogTheme(CustomAlertDialog.DIALOG_THEME.HAS_TITLE_TWO);
            mAlertDialog.setContent(message);

        }
        if (!mAlertDialog.isShowing()){
            mAlertDialog.show();
        }
    }

    @Override
    public void showAlert(String content, CustomAlertDialog.DIALOG_THEME theme) {
        if (null == mAlertDialog) {
            mAlertDialog = new CustomAlertDialog(context, content, theme);
        } else {
            mAlertDialog.setDialogTheme(theme);
            mAlertDialog.setContent(content);
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    @Override
    public void showAlert(String content, CustomAlertDialog.DIALOG_THEME theme, String btnText) {
        if (null == mAlertDialog) {
            mAlertDialog = new CustomAlertDialog(context,"", content, btnText,theme);
        } else {
            mAlertDialog.setDialogTheme(theme);
            mAlertDialog.setContent(content);
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    /**
     * 对话框是否可取消
     *
     * @param enable
     */
    @Override
    public void setAlertDialogCancelable(boolean enable) {
        if (null != mAlertDialog) {
            mAlertDialog.setCancelable(enable);
            mAlertDialog.setCanceledOnTouchOutside(enable);
            if(!enable){
                mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
            }else{
                mAlertDialog.setOnKeyListener(null);
            }
        }
    }

    @Override
    public void hideAlert() {
        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    public boolean isShowing() {
        if (null != mAlertDialog && mAlertDialog.isShowing()) return true;
        if (null != mProgressDialog && mProgressDialog.isShowing()) return true;
        return false;
    }


}
