package com.yaozu.mvp.view.fragment.contact;

import android.content.Context;
import android.view.View;

import com.yaozu.mvp.R;
import com.yaozu.mvp.presenter.main.contact.ContactPresenter;
import com.yaozu.mvp.presenter.main.contact.IContactView;
import com.yaozu.mvp.view.base.BaseFragment;


/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/6
 * @Desc:
 */

public class ContactFragment extends BaseFragment<ContactPresenter> implements IContactView {
    @Override
    public void initViews(Context context, View root) {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new ContactPresenter(false);
        presenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact;
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
