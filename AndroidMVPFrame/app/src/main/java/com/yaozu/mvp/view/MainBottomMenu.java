package com.yaozu.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaozu.mvp.R;
import com.yaozu.mvp.constant.Constants;


/**
 * 主界面的底部按钮
 *
 * @author : shiyaozu
 * @version : [v1.0.0, 2017/3/14]
 * @see [相关类/方法]
 * @since [V1]
 */
public class MainBottomMenu extends LinearLayout implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    /**
     * 聊天
     * */
    private RelativeLayout mChatRl;
    private RadioButton mChatRBtn;
    private TextView mChatCountTv;
    /**
     * 联系人
     * */
    private RelativeLayout mContactRl;
    private RadioButton mContactRBtn;
    private TextView mContactCountTv;

    /**
     * 设置
     * */
    private RelativeLayout mSettingRl;
    private RadioButton mSettingRBtn;
    private TextView mSettingCountTv;

    /**
     * 回调
     * */
    private OnTabItemListener mListener;

    public MainBottomMenu(Context context) {
        super(context);
        initView(context);
        initData();
        initListener();
    }

    public MainBottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData();
        initListener();
    }

    public MainBottomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData();
        initListener();
    }

    public void setOnTabItemListener(OnTabItemListener mListener) {
        this.mListener = mListener;
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.common_view_footer,this);
        mChatRl = (RelativeLayout) findViewById(R.id.rl_chat);
        mChatRBtn = (RadioButton) findViewById(R.id.rbtn_chat);
        mChatCountTv = (TextView) findViewById(R.id.tv_unread_chat);

        mContactRl = (RelativeLayout) findViewById(R.id.rl_contact);
        mContactRBtn = (RadioButton) findViewById(R.id.rbtn_contact);
        mContactCountTv = (TextView) findViewById(R.id.tv_unread_contact);

        mSettingRl = (RelativeLayout) findViewById(R.id.rl_setting);
        mSettingRBtn = (RadioButton) findViewById(R.id.rbtn_setting);
        mSettingCountTv = (TextView) findViewById(R.id.tv_unread_setting);
    }

    private void initData(){
        mChatRBtn.setChecked(false);
        mContactRBtn.setChecked(false);
        mSettingRBtn.setChecked(false);
    }

    private void initListener(){
        mChatRl.setOnClickListener(this);
        mContactRl.setOnClickListener(this);
        mSettingRl.setOnClickListener(this);

        mChatRBtn.setOnCheckedChangeListener(this);
        mContactRBtn.setOnCheckedChangeListener(this);
        mSettingRBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_chat:
                if(mChatRBtn.isChecked()){
                    return;
                }
                mChatRBtn.setChecked(true);
                mContactRBtn.setChecked(false);
                mSettingRBtn.setChecked(false);
                break;
            case R.id.rl_contact:
                if(mContactRBtn.isChecked()){
                    return;
                }
                mChatRBtn.setChecked(false);
                mContactRBtn.setChecked(true);
                mSettingRBtn.setChecked(false);
                break;
            case R.id.rl_setting:
                if(mSettingRBtn.isChecked()){
                    return;
                }
                mChatRBtn.setChecked(false);
                mContactRBtn.setChecked(false);
                mSettingRBtn.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.rbtn_chat:
                if(b){
                    mContactRBtn.setChecked(false);
                    mSettingRBtn.setChecked(false);

                    if(mListener != null){
                        mListener.onItemSelected(Constants.MAIN_BOTTOM_MENU_CHAT_TYPE);
                    }
                }
                break;
            case R.id.rbtn_contact:
                if(b){
                    mChatRBtn.setChecked(false);
                    mSettingRBtn.setChecked(false);

                    if(mListener != null){
                        mListener.onItemSelected(Constants.MAIN_BOTTOM_MENU_CONTACT_TYPE);
                    }
                }
                break;
            case R.id.rbtn_setting:
                if(b){
                    mChatRBtn.setChecked(false);
                    mContactRBtn.setChecked(false);
                    if(mListener != null){
                        mListener.onItemSelected(Constants.MAIN_BOTTOM_MENU_SETTING_TYPE);
                    }
                }
                break;
        }
    }

    /**
     * 选中指定的item
     * @param bottomType
     */
    public void setItemIndex(int bottomType){
        switch (bottomType){
            case Constants.MAIN_BOTTOM_MENU_CHAT_TYPE:
                mChatRl.performClick();
                break;
            case Constants.MAIN_BOTTOM_MENU_CONTACT_TYPE:
                mContactRl.performClick();
                break;
            case Constants.MAIN_BOTTOM_MENU_SETTING_TYPE:
                mSettingRl.performClick();
                break;
        }
    }

    public void setUnReadCount(int bottomType,int count){
        switch (bottomType){
            case Constants.MAIN_BOTTOM_MENU_CHAT_TYPE:
                if(count > 0){
                    mChatCountTv.setVisibility(VISIBLE);
                    String text = count > 99 ? (99+"+") : (count+"");
                    mChatCountTv.setText(text);
                }else{
                    mChatCountTv.setText("");
                    mChatCountTv.setVisibility(GONE);
                }
                break;
            case Constants.MAIN_BOTTOM_MENU_CONTACT_TYPE:
                if(count > 0){
                    mContactCountTv.setVisibility(VISIBLE);
                    String text = count > 99 ? (99+"+") : (count+"");
                    mContactCountTv.setText(text);
                }else{
                    mContactCountTv.setText("");
                    mContactCountTv.setVisibility(GONE);
                }
                break;
            case Constants.MAIN_BOTTOM_MENU_SETTING_TYPE:
                if(count > 0){
                    mSettingCountTv.setVisibility(VISIBLE);
                    String text = count > 99 ? (99+"+") : (count+"");
                    mSettingCountTv.setText(text);
                }else{
                    mSettingCountTv.setText("");
                    mSettingCountTv.setVisibility(GONE);
                }
                break;
        }
    }

    public interface OnTabItemListener {
        void onItemSelected(int type);
    }

}
