package com.mobcb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobcb.refreshlayout.R;


/**
 * <交互对话框>
 *
 * @author shyaozu
 * @version [版本号, 2016/6/12]
 * @see [相关类/方法]
 * @since [V1]
 */
public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    private Context context;

    /**
     * 标记
     */
    private int tag = -1;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 单个按钮文字
     */
    private String btntext;

    /**
     * 标题view
     */
    private TextView titleTextView;
    /**
     * 内容view textSize 28
     */
    private TextView contenTextView;
    /**
     * 内容view textSize 32
     */
    private TextView contenTextView1;
    /**
     * 输入框RelativeLayout
     */
    private RelativeLayout mInputContainer;
    /**
     * 输入框清除按钮
     */
    private ImageView mInputClear;
    /**
     * 输入框
     */
    private EditText mInput;
    /**
     * 进度条
     */
    private ProgressBar mProgress;

    /**
     * 进度条对应的 tv
     */
    private TextView mPercentTv;
    /**
     * 两个按钮container
     */
    private LinearLayout button_container_two;
    /**
     * 取消
     */
    private Button cancel;
    /**
     * 确定
     */
    private Button ok;
    /**
     * 单个按钮
     */
    private Button singleOk;
    /**
     * dialog主题
     */
    private DIALOG_THEME theme;
    /**
     * 当前dialog主题
     */
    private DIALOG_THEME currentTheme;
    /**
     * 回调
     */
    private AlertDialogListener commDialogListener;
    /**
     * 内容对齐方式
     */
    private int contentGravity;// 控制内容对齐方式

    public enum DIALOG_THEME {
        HAS_TITLE_TWO,
        HAS_TITLE_NO_CONTENT_TWO,
        HAS_TITLE_TWO_INPUT,
        HAS_TITLE_TWO_PROGRESS,
        HAS_TITLE_ONE,
        HAS_TITLE_ONE_INPUT,
        HAS_TITLE_ONE_PROGRESS,
        NO_TITLE_TWO,
        NO_TITLE_TWO_INPUT,
        NO_TITLE_TWO_PROGRESS,
        NO_TITLE_ONE,
        NO_TITLE_ONE_INPUT,
        NO_TITLE_ONE_PROGRESS,
        NO_TITLE_NONE_PROGRESS,
    }


    public CustomAlertDialog(Context context) {
        this(context, null, null, null, DIALOG_THEME.HAS_TITLE_TWO, Gravity.CENTER);
    }

    /**
     * MTAlertDialog
     *
     * @param context
     * @param content 内容
     */
    public CustomAlertDialog(Context context, String content) {
        this(context, null, content, null, DIALOG_THEME.HAS_TITLE_TWO, Gravity.CENTER);
    }

    public CustomAlertDialog setAlertDialogListener(AlertDialogListener commDialogListener) {
        this.commDialogListener = commDialogListener;
        return this;
    }

    /**
     * MTAlertDialog
     *
     * @param context
     * @param title   标题
     * @param content 内容
     */
    public CustomAlertDialog(Context context, String title, String content) {
        this(context, title, content, null, DIALOG_THEME.HAS_TITLE_TWO, Gravity.CENTER);
    }

    /**
     * MTAlertDialog
     *
     * @param context
     * @param content 内容
     * @param theme   主题
     */
    public CustomAlertDialog(Context context, String content, DIALOG_THEME theme) {
        this(context, null, content, null, theme, Gravity.CENTER);
    }

    /**
     * MTAlertDialog
     *
     * @param context
     * @param title   标题
     * @param content 内容
     * @param btntext 单个按钮问题
     * @param theme   主题
     */
    public CustomAlertDialog(Context context, String title, String content, String btntext, DIALOG_THEME theme) {
        this(context, title, content, btntext, theme, Gravity.CENTER);
    }

    /**
     * MTAlertDialog
     *
     * @param context
     * @param title          标题
     * @param content        内容
     * @param btntext        单个按钮问题
     * @param theme          主题
     * @param contentGravity 内容对其方式
     */
    public CustomAlertDialog(Context context, String title, String content, String btntext, DIALOG_THEME theme,
                             int contentGravity) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.title = title;
        this.content = content;
        this.btntext = btntext;
        this.theme = theme;
        if (context instanceof AlertDialogListener) {
            commDialogListener = (AlertDialogListener) context;
        }
        this.contentGravity = contentGravity;
    }

    public CustomAlertDialog setTMAlertDialogListener(AlertDialogListener commDialogListener) {
        this.commDialogListener = commDialogListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alert_dialog);
        titleTextView = (TextView) findViewById(R.id.dialog_title);

        if (null != title)
            titleTextView.setText(title);

        contenTextView = (TextView) findViewById(R.id.dialog_msg_28);
        contenTextView1 = (TextView) findViewById(R.id.dialog_msg_32);
        if (!TextUtils.isEmpty(content)) {
            if (theme == DIALOG_THEME.NO_TITLE_ONE || theme == DIALOG_THEME.NO_TITLE_TWO) {
                contenTextView1.setText(content);
            } else
                contenTextView.setText(content);
        }
        contenTextView.setGravity(contentGravity);

        button_container_two = (LinearLayout) findViewById(R.id.dialog_two_btn_container);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(this);
        ok = (Button) findViewById(R.id.dialog_ok);
        ok.setOnClickListener(this);
        singleOk = (Button) findViewById(R.id.dialog_singelok);
        if (null != btntext) {
            singleOk.setText(btntext);
        }
        singleOk.setOnClickListener(this);

        mInputContainer = (RelativeLayout) findViewById(R.id.dialog_input_r);
        mInput = (EditText) findViewById(R.id.dialog_input);
        mInputClear = (ImageView) findViewById(R.id.dialog_input_clear);
        mInputClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInput.setText("");
            }
        });

        mProgress = (ProgressBar) findViewById(R.id.dialog_progress);

        mPercentTv = (TextView) findViewById(R.id.dialog_percent_tv);

        setDialogTheme(theme);
    }

    /**
     * setDialogTheme
     *
     * @param newTheme 自定义主题，控制显示
     */
    public void setDialogTheme(DIALOG_THEME newTheme) {
        //每次重新设置主题的时候，清空输入框
        if (null != mInput) mInput.setText("");

        if (newTheme == currentTheme)
            return;
        currentTheme = newTheme;
        switch (newTheme) {
            case HAS_TITLE_TWO:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case HAS_TITLE_NO_CONTENT_TWO:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case HAS_TITLE_TWO_INPUT:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mInputContainer.setVisibility(View.VISIBLE);
                mInput.setText("");
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case HAS_TITLE_TWO_PROGRESS:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case HAS_TITLE_ONE:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case HAS_TITLE_ONE_INPUT:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.VISIBLE);
                mInput.setText("");
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case HAS_TITLE_ONE_PROGRESS:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mPercentTv.setVisibility(View.VISIBLE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case NO_TITLE_TWO:
                titleTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case NO_TITLE_TWO_INPUT:
                titleTextView.setVisibility(View.GONE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mInputContainer.setVisibility(View.VISIBLE);
                mInput.setText("");
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case NO_TITLE_TWO_PROGRESS:
                titleTextView.setVisibility(View.GONE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.VISIBLE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
            case NO_TITLE_ONE:
                titleTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case NO_TITLE_ONE_INPUT:
                titleTextView.setVisibility(View.GONE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.VISIBLE);
                mInput.setText("");
                mProgress.setVisibility(View.GONE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case NO_TITLE_ONE_PROGRESS:
                titleTextView.setVisibility(View.GONE);
                contenTextView.setVisibility(View.VISIBLE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.VISIBLE);
                break;
            case NO_TITLE_NONE_PROGRESS:
                titleTextView.setVisibility(View.VISIBLE);
                contenTextView.setVisibility(View.GONE);
                contenTextView1.setVisibility(View.GONE);
                button_container_two.setVisibility(View.GONE);
                mInputContainer.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                mPercentTv.setVisibility(View.GONE);
                singleOk.setVisibility(View.GONE);
                break;
        }
    }


    public void setTag(int tag) {
        this.tag = tag;
    }

    /**
     * 显示dialog  必须要先show才能更改ui
     *
     * @return
     */
    public CustomAlertDialog showDialog() {
        super.show();
        return this;
    }

    /**
     * setTitle
     *
     * @param title 文字
     */
    public void setTitle(String title) {
        if (null != title) {
            titleTextView.setText(title);
        }
    }

    /**
     * setContent 消息内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        if (null != content) {
            if (currentTheme == DIALOG_THEME.NO_TITLE_ONE || currentTheme == DIALOG_THEME.NO_TITLE_TWO)
                contenTextView1.setText(content);
            else
                contenTextView.setText(content);
        }
    }

    /**
     * setCancelText 设置取消按钮的文字
     *
     * @param content 文字
     */
    public void setCancelText(String content) {
        if (null != content) {
            cancel.setText(content);
        }
    }

    /**
     * setOKText 设置确定按钮的文字
     *
     * @param content 文字
     */
    public CustomAlertDialog setOKText(String content) {
        if (null != content) {
            ok.setText(content);
        }
        return this;
    }

    /**
     * setSingleBtnText 设置一个按钮的文字
     *
     * @param content 文字
     */
    public void setSingleBtnText(String content) {
        if (null != content) {
            singleOk.setText(content);
        }
    }

    /**
     * setProgress 设置进度条的进度
     */
    public void setProgress(int progress) {
        mProgress.setProgress(progress);
        mPercentTv.setVisibility(View.VISIBLE);
        String temp = progress + "%";
        mPercentTv.setText(temp);
    }

    /**
     * setProgress 设置进度条对应的进度文案
     */
    public void setPercentTv(String percentTx) {
        mPercentTv.setVisibility(View.VISIBLE);
        mPercentTv.setText(percentTx);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_cancel){
            if (null != commDialogListener) {
                commDialogListener.clickCancel(tag);
            }
            this.dismiss();
        } else if( id ==  R.id.dialog_ok){
            if (null != commDialogListener) {
                commDialogListener.clickOk(mInput.getVisibility() == View.VISIBLE ? mInput.getText().toString() : "", tag);
            }
            //此处应对当前的AlertDialog的主题做个判断，通常在有输入框且输入框为空时点击确定会弹出Toast，并且Dialog不消失
            if (currentTheme != DIALOG_THEME.NO_TITLE_TWO_INPUT) {
                this.dismiss();
            }
        } else if(id ==  R.id.dialog_singelok){
            if (null != commDialogListener) {
                commDialogListener.singleOk(mInput.getVisibility() == View.VISIBLE ? mInput.getText().toString() : "", tag);
            }
            this.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (null != commDialogListener) {
            commDialogListener.clickCancel(tag);
        }
        this.dismiss();
    }

    public interface AlertDialogListener {
        /**
         * 取消操作
         */
        void clickCancel(int tag);

        /**
         * 确定操作
         */
        void clickOk(String input, int tag);

        /**
         * 单个确定操作
         */
        void singleOk(String input, int tag);
    }

}


