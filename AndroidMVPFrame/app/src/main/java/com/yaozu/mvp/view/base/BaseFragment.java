package com.yaozu.mvp.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mobcb.toolbar.CustomActionBar;
import com.yaozu.mvp.R;
import com.yaozu.mvp.func.CreateInit;
import com.yaozu.mvp.func.PublishActivityCallBack;
import com.yaozu.mvp.presenter.BasePresenter;

/**
 * <页面基础公共功能实现>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/21]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class BaseFragment<V extends BasePresenter> extends Fragment implements CreateInit,
        PublishActivityCallBack, View.OnClickListener,CustomActionBar.ActionBarListener {

    public V presenter;
    /**
     * 布局view
     */
    protected View mRoot = null;

    public final String TAG = this.getClass().getSimpleName();

    /**
     * ActionBar 头部信息
     */
    public CustomActionBar mHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null ) {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            if (getActivity() == null || getActivity().isFinishing())
                return mRoot;
            // 初始化页面组件
            initViews(getActivity(), mRoot);
            initHeaderView(mRoot);
            // 绑定事件监听
            initListeners();
            // 初始化页面数据
            initData();
        } else {
            final ViewParent parent = mRoot.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mRoot);
            }
        }
        return mRoot;
    }

    /**
     * 初始化加载view
     */
    private void initHeaderView(View mRoot) {
        mHeader = (CustomActionBar) mRoot.findViewById(R.id.common_header);
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
    public void onClick(View v) {

    }

    //------------PublishActivityCallBack-----------------

    @Override
    public void startActivity(Class<?> openClass, @Nullable Bundle bundle) {
        Intent intent = new Intent(getActivity(), openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void startActivityForResult(Class<?> openClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     *
     * 这个从fragment 跳转的，保证返回的result 先从 fragment 外部的activity走一下
     * @param openClass
     * @param requestCode
     * @param bundle
     */
    public void startActForResultBackToAct(Class<?> openClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, requestCode);
    }

    //------------Fragment生命周期-------------

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public boolean isFragmentActivity() {
        return false;
    }
}