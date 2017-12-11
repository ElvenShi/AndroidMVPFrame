package com.yaozu.mvp.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.mobcb.dialog.CustomAlertDialog;
import com.mobcb.widget.ToastUtil;
import com.yaozu.mvp.MyApplication;
import com.yaozu.mvp.R;
import com.yaozu.mvp.presenter.loading.ILoadingView;
import com.yaozu.mvp.presenter.loading.LoadingPresenter;
import com.yaozu.mvp.utils.log.Logger;
import com.yaozu.mvp.view.base.BaseActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * @author : shiyaozu
 * @version : V1.0
 * @date : 2017/4/25
 * @Desc:
 */
@RuntimePermissions
public class LoadingActivity extends BaseActivity<LoadingPresenter> implements ILoadingView {

    /**
     * 标记app是否已经获取到需要的权限
     */
    private boolean hasPermission = false;

    /**
     * 进入启动页的时候动态检查权限
     * @param context
     * @param root
     */
    @Override
    public void initViews(Context context, View root) {
        MyApplication.setHasLoading(true);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        }
        LoadingActivityPermissionsDispatcher.getMultiWithPermissionCheck(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    //获取多个权限
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    public void getMulti() {
        Toast.makeText(this, "getMulti", Toast.LENGTH_SHORT).show();
        hasPermission();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public int getToolBarId() {
        return 0;
    }

    @Override
    public String getName() {
        return "引导页";
    }

    public void hasPermission() {
        hasPermission = true;
        initApplication();
    }

    /**
     * 向用户说明为什么需要这些权限（可选）
     */
    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showPermissionRationale(final PermissionRequest request) {
        String content = String.format(getString(R.string.permission_tip_none), getString(R.string.external_storage),
                getString(R.string.storage_persional_info));
        mLayerHelper.showAlert(getString(R.string.get_storage), content);
        mLayerHelper.getAlertDialog().setCancelText(getString(R.string.cancel));
        mLayerHelper.getAlertDialog().setOKText(getString(R.string.sure));
        mLayerHelper.getAlertDialog().setAlertDialogListener(new CustomAlertDialog.AlertDialogListener() {
            @Override
            public void clickCancel(int tag) {
                request.cancel();
            }

            @Override
            public void clickOk(String input, int tag) {
                request.proceed();
            }

            @Override
            public void singleOk(String input, int tag) {

            }
        });
//        mLayerHelper.getAlertDialog().setTag(code);
        mLayerHelper.getAlertDialog().setCancelable(false);
        mLayerHelper.getAlertDialog().setCanceledOnTouchOutside(false);
        mLayerHelper.getAlertDialog().show();
    }

    /**
     * 权限都获取到，初始化应用
     */
    private void initApplication() {
        try {
            ((MyApplication) getApplication()).initPermissionData();
        } catch (Exception e) {
            Logger.e("initApplication error");
        }
        presenter = new LoadingPresenter(false);
        presenter.attachView(this);
        presenter.initData();
    }

    @Override
    public void skipToGuide() {

    }

    @Override
    public void skipToCurrentGuide(int guideVersion) {

    }

    @Override
    public void skipToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void skipToLogin() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (hasPermission) {
            getApplication().onTerminate();
        }
    }

    /**
     * 用户拒绝授权回调（可选）
     */
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void storageDenied(){
        ToastUtil.showBottomToast(this,"您拒绝了App对手机外部存储的读写权限");
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    public void phoneStateDenied(){
        ToastUtil.showBottomToast(this,"您拒绝了READ_PHONE_STATE");
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    public void phoneStateNeverAskAgain(){
        ToastUtil.showBottomToast(this,"您拒绝了READ_PHONE_STATE，并且您选择了不在提醒");
    }

    /**
     * 用户勾选了“不再提醒”时调用（可选）
     * 在这里可以标记
     */
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void storageNeverAskAgain(){
        ToastUtil.showBottomToast(this,"您拒绝了App对手机外部存储的读写权限，并且您选择了不在提醒");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoadingActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}
