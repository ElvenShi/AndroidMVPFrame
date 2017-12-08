package com.yaozu.mvp.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yaozu.base.library.utils.ActivityStackManager;
import com.yaozu.mvp.MyApplication;
import com.yaozu.mvp.view.CreateInit;
import com.yaozu.mvp.view.PresentationLayerFuncHelper;
import com.yaozu.mvp.view.PublishActivityCallBack;
import com.yaozu.mvp.contract.base.BasePresenter;
import com.yaozu.mvp.view.activity.LoadingActivity;

/**
 * <基础activity>
 *
 * @author shiyaozu
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [V1]
 */
public abstract class AbsBaseActivity extends AppCompatActivity
        implements CreateInit, PublishActivityCallBack{

    /**
     * 交互层
     */
    public PresentationLayerFuncHelper mLayerHelper;

    public BasePresenter presenter;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkHasLoading(this)) {
            System.out.println("BaseActivity savedInstanceState = [onCreate]");
            mLayerHelper = new PresentationLayerFuncHelper(this);
            setContentView(getLayoutId());
            initViews(this, null);
            initListeners();
            initData();
            //加入堆栈
            ActivityStackManager.getInstance().addActivity(this);
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
            // 跳转到loading页面，只要app还存活就进行loading页面
            Intent intent = new Intent();
            intent.setClass(activity, LoadingActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
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
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        ActivityStackManager.getInstance().removeActivity(this);
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
