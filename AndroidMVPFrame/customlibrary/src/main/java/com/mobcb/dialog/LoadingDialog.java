package com.mobcb.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mobcb.refreshlayout.R;


/**
 * 类描述：加载中提示框
 * 创建人：shiyaozu
 * 创建时间：2016/6/8 20:09
 */
public class LoadingDialog extends ProgressDialog {

    private TextView mTxt;
    private String title;

    public LoadingDialog(Context context, int theme) {
        super(context, R.style.MTDialog);
    }

    public LoadingDialog(Context context) {
        super(context, R.style.MTDialog);
    }

    public LoadingDialog(Context context, String title) {
        super(context, R.style.MTDialog);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        mTxt = (TextView) findViewById(R.id.loading_dialog_txt);
        if (!TextUtils.isEmpty(title))
            mTxt.setText(title);
        else
            mTxt.setVisibility(View.GONE);
        //默认点击外部，不取消
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    /**
     * setLoadingMsg
     *
     * @param msg 加载提示语
     */
    public void setLoadingMsg(String msg) {
        if (!TextUtils.isEmpty(msg))
            mTxt.setText(msg);
        else
            mTxt.setVisibility(View.GONE);
    }
}
