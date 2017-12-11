package com.yaozu.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.yaozu.mvp.R;
import com.yaozu.mvp.constant.Constants;
import com.yaozu.mvp.presenter.main.IMainView;
import com.yaozu.mvp.presenter.main.MainPresenter;
import com.yaozu.mvp.utils.FragmentHelper;
import com.yaozu.mvp.view.MainBottomMenu;
import com.yaozu.mvp.view.base.BaseActivity;
import com.yaozu.mvp.view.fragment.chat.ChatFragment;
import com.yaozu.mvp.view.fragment.contact.ContactFragment;
import com.yaozu.mvp.view.fragment.setting.SettingFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, View.OnClickListener, MainBottomMenu.OnTabItemListener {

    /**
     * 底部
     */
    private MainBottomMenu mMainBottomMenu;
    private FragmentHelper fragmentHelper;
    private boolean bIsFirstLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            bIsFirstLoading = savedInstanceState.getBoolean("bIsFirstLoading", bIsFirstLoading);
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initViews(Context context, View root) {
        mMainBottomMenu = (MainBottomMenu) findViewById(R.id.bottom_menu);
        fragmentHelper = new FragmentHelper(this,R.id.activity_main_container);
    }

    @Override
    public void initListeners() {
        mMainBottomMenu.setOnTabItemListener(this);
    }

    @Override
    public void initData() {
//        presenter = new MainPresenter(true);//传入true表示注册event Bus
        presenter = new MainPresenter(false);
        presenter.attachView(this);
        mMainBottomMenu.setItemIndex(Constants.MAIN_BOTTOM_MENU_CHAT_TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getToolBarId() {
        return R.id.common_header;
    }

    @Override
    public String getName() {
        return "主界面";
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.select_dialog:
//                break;
//        }
    }

    /**
     * 底部导航按钮TAB选中事件
     * @param type
     */
    @Override
    public void onItemSelected(int type) {
        switch (type) {
            case Constants.MAIN_BOTTOM_MENU_CHAT_TYPE:
                turnToFragment(ChatFragment.class.getSimpleName());
                break;
            case Constants.MAIN_BOTTOM_MENU_CONTACT_TYPE:
                turnToFragment(ContactFragment.class.getSimpleName());
                break;
            case Constants.MAIN_BOTTOM_MENU_SETTING_TYPE:
                turnToFragment(SettingFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        removeAllFragments();
        super.onDestroy();
    }

    /**
     * 跳转至需要显示的fragment页面
     *
     */
    private void turnToFragment(String fragment) {
        fragmentHelper.showFragment(fragment);
    }

    /**
     * 退出登录 或者  清除缓存后，需要移除 相应的fragment
     *
     */
    private void removeAllFragments() {
        fragmentHelper.clearFragments();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("bIsFirstLoading", bIsFirstLoading);
    }
}
