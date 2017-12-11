package com.yaozu.mvp.view.fragment.chat;

import android.content.Context;
import android.view.View;

import com.yaozu.mvp.R;
import com.yaozu.mvp.presenter.main.chat.ChatPresenter;
import com.yaozu.mvp.presenter.main.chat.IChatView;
import com.yaozu.mvp.view.base.BaseFragment;


/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/6
 * @Desc:
 */

public class ChatFragment extends BaseFragment<ChatPresenter> implements IChatView {

    @Override
    public void initViews(Context context, View root) {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        presenter = new ChatPresenter(false);
        presenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public int getToolBarId() {
        return 0;
    }

    @Override
    public String getName() {
        return "会话";
    }

    @Override
    public void leftBtnClick() {

    }

    @Override
    public void rightBtnClick() {

    }
}
