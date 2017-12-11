package com.yaozu.mvp.view.fragment.setting;

import android.content.Context;
import android.view.View;

import com.yaozu.mvp.R;
import com.yaozu.mvp.presenter.main.setting.ISettingView;
import com.yaozu.mvp.presenter.main.setting.SettingPresenter;
import com.yaozu.mvp.view.base.BaseFragment;


/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/6
 * @Desc:
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements ISettingView {

    @Override
    public void initViews(Context context, View root) {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new SettingPresenter(false);
        presenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public int getToolBarId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void leftBtnClick() {

    }

    @Override
    public void rightBtnClick() {

    }
}
