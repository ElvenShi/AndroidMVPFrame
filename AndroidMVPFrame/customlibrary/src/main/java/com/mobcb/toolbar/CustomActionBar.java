package com.mobcb.toolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobcb.helper.StatusBarHelper;
import com.mobcb.refreshlayout.R;


/**
 * @author shiyaozu
 * @version [版本号, 2016/6/27]
 * @desc 这是一个自定义标题栏，通用型标题栏设计
 * @see [相关类/方法]
 * @since [V1]
 */
public class CustomActionBar extends FrameLayout implements View.OnClickListener {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * title container
     */
    private RelativeLayout mAction_container;

    /**
     * 标题为文案的 ll
     */
//    private LinearLayout mAction_txt_title_ll;
    private View mTopView;
    /**
     * 左 中 右
     */
    private TextView mAction_title, mAction_right, mAction_back;

    private ActionBarListener mTMActionBarListener;

    private View mAction_title_bottom_diver_tv;//分割线

    public CustomActionBar(Context context) {
        super(context);
        init(context);
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * @param context 初始化
     */
    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_title, null);
        mAction_container = (RelativeLayout) view.findViewById(R.id.common_title_container);

        //为了适应沉浸式状态栏
        mTopView = view.findViewById(R.id.top_view);
        int statusBarHeight = StatusBarHelper.getStatusbarHeight(context);
        mTopView.getLayoutParams().height = statusBarHeight;

        mAction_title = (TextView) view.findViewById(R.id.tv_title);
        mAction_right = (TextView) view.findViewById(R.id.tv_right);
        mAction_back = (TextView) view.findViewById(R.id.back);

        mAction_title_bottom_diver_tv = view.findViewById(R.id.tv_header_bottom_diver);

        mAction_right.setOnClickListener(this);
        mAction_back.setOnClickListener(this);
        mAction_title.setOnClickListener(this);

        addView(view);
    }

    /**
     * 设置ActionBar的背景颜色
     *
     * @param bgColor 背景色Id
     */
    public void setActionBgColor(int bgColor) {
        mTopView.setBackgroundColor(ContextCompat.getColor(mContext, bgColor));
        mAction_title.setBackgroundColor(ContextCompat.getColor(mContext, bgColor));
    }

//    /**
//     * 设置标题栏右侧菜单距离右边的margin
//     * @param marginRight
//     */
//    public void setRightActionMarginRight(int marginRight){
//        RelativeLayout.LayoutParams newParams = (RelativeLayout.LayoutParams) mAction_more_container.getLayoutParams();
//        newParams.setMargins(0,0, DensityUtil.dip2px(marginRight),0);
//        mAction_more_container.setLayoutParams(newParams);
//    }

//    /**
//     * 设置标题栏左侧菜单距离左边的margin
//     * @param marginLeft
//     */
//    public void setLeftActionMarginLeft(int marginLeft){
//        RelativeLayout.LayoutParams newParams = (RelativeLayout.LayoutParams) mAction_back_container.getLayoutParams();
//        newParams.setMargins(DensityUtil.dip2px(marginLeft),0,0,0);
//        mAction_more_container.setLayoutParams(newParams);
//    }

    public RelativeLayout getAction_container() {
        return mAction_container;
    }

    /**
     * 设置ActionBar的背景颜色和字体颜色
     *
     * @param bgColor    背景色Id
     * @param titleColor title字体颜色
     */
    public void setActionBgColor(int bgColor, int titleColor) {
        mTopView.setBackgroundColor(ContextCompat.getColor(mContext, bgColor));
        mAction_title.setBackgroundColor(ContextCompat.getColor(mContext, bgColor));
        mAction_title.setTextColor(ContextCompat.getColor(mContext, titleColor));
    }

    /**
     * 设置ActionBar的返回键信息
     * <p/>
     * 返回按钮
     * 可以为箭头返回 也可以自定义文字
     * 当使用文字时将背景去除
     *
     * @param btnMsg
     */
    public void setActionLeftTxt(String btnMsg) {
        if (!TextUtils.isEmpty(btnMsg)) {
            mAction_back.setText(btnMsg);
            mAction_back.setBackgroundResource(0);
        }
    }

    /**
     * 设置返回键的背景图
     *
     * @param drawableId
     */
    public void setLeftBackground(int drawableId) {
        mAction_back.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mAction_back.setBackground(ContextCompat.getDrawable(mContext, drawableId));
        else
            mAction_back.setBackgroundDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    /**
     * 设置左侧按钮的drawable left
     *
     * @param drawableId
     */
    public void setDrawableLeft(int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mAction_back.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置标题字体颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        mAction_title.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置标题字体大小
     *
     * @param size
     */
    public void setTitleTextSize(int size) {
        mAction_title.setTextSize(size);
    }

    /**
     * 设置右侧按钮的drawable right
     *
     * @param drawableId
     */
    public void setDrawableRight(int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mAction_right.setCompoundDrawables(null, null, drawable, null);
//        mAction_right.setPadding(0,0,20,0);
    }

    /**
     * 设置ActionBar的返回键是否显示
     *
     * @param visible
     */
    public void setActionLeftVisible(int visible) {
        mAction_back.setVisibility(visible);
    }

    /**
     * 设置ActionBar的右键信息
     * 可以为箭头返回 也可以自定义文字
     * 当使用文字时将背景去除
     *
     * @param btnMsg
     */
    public void setActionRightText(String btnMsg) {
        if (btnMsg != null) {
//            mAction_right.setPadding(0,0,20,0);
            mAction_right.setText(btnMsg);
            mAction_right.setBackgroundResource(0);
        }
    }

    /**
     * 设置标题右边是否可用 常用场景 不可提交或确定
     *
     * @param isEnable
     */
    public void setRightActionEnableStatus(boolean isEnable) {
        mAction_right.setEnabled(isEnable);
    }

    /**
     * 设置右上角 按钮 字体颜色
     *
     * @param color
     */
    public void setRightActionTextColor(int color) {
        mAction_right.setTextColor(mContext.getResources().getColor(color));
    }

    /**
     * 设置右键的背景图
     *
     * @param drawableId
     */
    public void setRightBackground(int drawableId) {
        mAction_right.setText("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mAction_right.setBackground(ContextCompat.getDrawable(mContext, drawableId));
        else
            mAction_right.setBackgroundDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    /**
     * 设置ActionBar的右键是否显示
     *
     * @param visible
     */
    public void setActionRightVisible(int visible) {
        mAction_right.setVisibility(visible);
    }

    /**
     * 普通标题
     * 设置 title的名称
     *
     * @param btnMsg
     */
    public void setActionTextTitle(String btnMsg) {
        mAction_title.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(btnMsg)) {
            mAction_title.setText(btnMsg);
        }
    }

    /**
     * 设置普通标题是否可见
     *
     * @param visible
     */
    public void setActionTextTitleVisible(int visible) {
        mAction_title.setVisibility(visible);
    }


    /**
     * 普通标题
     * 返回当前标题名称
     *
     * @return
     */
    public String getActionTextTitle() {
        if (null != mAction_title)
            return mAction_title.getText().toString();
        return "";
    }

    /**
     * 设置底部横线 不展示
     */
    public void hideTitleBottomDiver() {
        mAction_title_bottom_diver_tv.setVisibility(INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            mTMActionBarListener.leftBtnClick();
        } else if (v.getId() == R.id.tv_right) {
            mTMActionBarListener.rightBtnClick();
        } else if (v.getId() == R.id.tv_title) {

        }
    }

    /**
     * 设置SFActionBar的监听
     *
     * @param TMActionBarListener
     */
    public void setTMActionBarListener(ActionBarListener TMActionBarListener) {
        mTMActionBarListener = TMActionBarListener;
    }

    /**
     * ActionBar的监听
     */
    public interface ActionBarListener {
        /**
         * 返回按钮点击事件
         */
        void leftBtnClick();

        /**
         * 更多按钮点击事件
         */
        void rightBtnClick();
    }
}
