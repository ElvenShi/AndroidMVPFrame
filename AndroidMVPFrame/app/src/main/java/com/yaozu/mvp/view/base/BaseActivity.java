package com.yaozu.mvp.view.base;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.mobcb.toolbar.CustomActionBar;
import com.yaozu.base.library.utils.DensityUtil;
import com.yaozu.mvp.MyApplication;
import com.yaozu.mvp.R;
import com.yaozu.mvp.func.CreateInit;
import com.yaozu.mvp.func.PresentationLayerFuncHelper;
import com.yaozu.mvp.func.PublishActivityCallBack;
import com.yaozu.mvp.presenter.BasePresenter;
import com.yaozu.mvp.utils.ActivityManager;
import com.yaozu.mvp.utils.ActivityUtil;
import com.yaozu.mvp.view.activity.LoadingActivity;

/**
 * <基础activity>
 *
 * @author shiyaozu
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class BaseActivity<V extends BasePresenter> extends AppCompatActivity
        implements CreateInit, PublishActivityCallBack, CustomActionBar.ActionBarListener {

    /**
     * 交互层
     */
    public PresentationLayerFuncHelper mLayerHelper;

    public V presenter;

    /**
     * CustomActionBar 头部信息
     */
    public CustomActionBar mHeader;

    public final String TAG = this.getClass().getSimpleName();

    private boolean keyboardListenersAttached = false;

    /**
     * 导航栏高度
     */
    public int navigationHeight;

    /**
     * 键盘高度
     */
    public int keyboardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkHasLoading(this)) {
            System.out.println("BaseActivity savedInstanceState = [onCreate]");
            if (!isDoStatusBarSelf()) {
                ActivityUtil.flymeSetStatusBarLightMode(getWindow(), true, R.color.app_status_bg_color);
                ActivityUtil.mIUISetStatusBarLightMode(getWindow(), true, R.color.app_status_bg_color);
            }
            mLayerHelper = new PresentationLayerFuncHelper(this);
            setContentViewBefore();
            setContentView(getLayoutId());
            initViews(this, null);
            initHeaderView(getToolBarId());
            initListeners();
            initData();
            //加入堆栈
            ActivityManager.getInstance().addActivity(this);
        }
    }


    /**
     * 控制键盘弹出和隐藏时的布局变化
     *
     * @param rootLayout
     */
    public void controlKeyboardLayout(final View rootLayout) {
        if (keyboardListenersAttached) {
            return;
        }

        initKeyboardHeight();
        initNavigationHeight();
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean isSoftKeyboardOpened = false;

            @Override
            public void onGlobalLayout() {
                int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
                Rect rect = new Rect();
                rootLayout.getWindowVisibleDisplayFrame(rect);
                int contentViewTop = rect.top;

                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(BaseActivity.this);

                if (isSoftKeyboardOpened && heightDiff <= contentViewTop + navigationHeight) {
                    isSoftKeyboardOpened = false;
                    onKeyBoardHide();

                    Intent intent = new Intent("KeyboardWillHide");
                    broadcastManager.sendBroadcast(intent);
                } else if (!isSoftKeyboardOpened && heightDiff > contentViewTop + navigationHeight){
                    keyboardHeight = heightDiff - contentViewTop - navigationHeight;
                    isSoftKeyboardOpened = true;
                    onKeyBoardShow(keyboardHeight);

                    Intent intent = new Intent("KeyboardWillShow");
                    intent.putExtra("KeyboardHeight", keyboardHeight);
                    broadcastManager.sendBroadcast(intent);
                }
            }
        });
        keyboardListenersAttached = true;
    }

    public void onKeyBoardHide() {

    }

    public void onKeyBoardShow(int keyboardHeight) {

    }

    /**
     * 兼容5.0之后，初始化虚拟导航栏的高度
     */
    private void initNavigationHeight() {
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        if (!hasHomeKey) { //判断没有HOME键时存在导航栏
            Resources resources = getResources();
            navigationHeight = resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
        }
    }

    /**
     * 初始化键盘高度
     */
    private void initKeyboardHeight() {
        // 初始化输入法和底部工具条高度
        if (keyboardHeight == 0) {
            keyboardHeight = Math.min(DensityUtil.dip2px(this,253), DensityUtil.getYScreenpx(this) / 2); // 估算高度
        }
    }

    /**
     * 检查当前进程是否经历过loading页
     * <p>
     * 如果没有经历过loading页则表示当前进程被杀，需要重新经历loading页
     *
     * @param activity 当前activity
     */
    private boolean checkHasLoading(AppCompatActivity activity) {
        if (MyApplication.isHasLoading() || activity instanceof LoadingActivity) {
            return true;
        } else {
            Intent intent = new Intent();
            intent.setClass(activity, LoadingActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
    }

    /**
     * 是否子Activity自己处理状态栏
     *
     * @return 如果自己处理状态栏 返回true
     */
    public boolean isDoStatusBarSelf() {

        return false;
    }


    /**
     * 空方法  可重写
     */
    public void setContentViewBefore() {

    }


    /**
     * 初始化加载view
     */
    public void initHeaderView(int headerId) {
        if (headerId == 0){
            return;
        }
        //CustomActionBar
        mHeader = (CustomActionBar) findViewById(headerId);
        if (null != mHeader) {
            setHeader();
            mHeader.setTMActionBarListener(this);
        }
    }

    /**
     * 设置SFActionBar的信息
     */
    public void setHeader() {
        mHeader.setActionLeftTxt(getLeftTxt());
    }

    /**
     * 设置标题栏左边文字按钮，如需设置 直接重写即可
     *
     * @return 标题左侧按钮文字
     */
    public String getLeftTxt() {
        return "";
    }

    @Override
    public void leftBtnClick() {
        finish();
    }

    @Override
    public void rightBtnClick() {

    }

    //------------PublishActivityCallBack-----------------
    @Override
    public void startActivity(Class<?> openClass, Bundle bundle) {
        Intent intent = new Intent(this, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> openClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    //------------Activity生命周期-------------
    @Override
    protected void onResume() {
        MyApplication.currentActivityName = this.getClass().getName();
        super.onResume();

//        if (!isFragmentActivity())
//            MobclickAgent.onPageStart(getName());   //统计页面
//        MobclickAgent.onResume(this);   //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (!isFragmentActivity())
//            MobclickAgent.onPageEnd(getName());
//        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        //返回操作，如果有对话框，则先隐藏对话框
        if (null != mLayerHelper && mLayerHelper.isShowing()) {
            mLayerHelper.hideProgressDialog();
            mLayerHelper.hideAlert();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 默认不包含fragment 当包含时 重写该方法 返回true
     *
     * @return
     */
    @Override
    public boolean isFragmentActivity() {
        return false;
    }

}
