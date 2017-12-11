package com.yaozu.mvp.presenter.loading;

import android.os.CountDownTimer;

import com.yaozu.mvp.presenter.BasePresenter;


/**
 * @author : Shiyaozu(Elven)
 * @version : V1.0
 * @date : 2017/5/5
 * @Desc:
 */

public class LoadingPresenter extends BasePresenter<ILoadingView> {

    public static final long LOAD_COUNT_DOWN_TIME = 2 * 1000;

    /**
     * loading页面是否获取焦点
     */
    private boolean hasFocus = false;
    /**
     * loading页面渲染开始时间
     */
    private long mStartTime = 0;
    /**
     * 麦通配置结束时间
     */
    private long mEndTime = 0;

    public LoadingPresenter(boolean openEventBus){
        super(openEventBus);
    }

    public void initData(){
        loadingStart();
    }

    /**
     * loading页面渲染开始
     *
     */
    public void loadingStart() {
        CountDownTimer countDownTimer = new CountDownTimer(LOAD_COUNT_DOWN_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                skipToTargetActivity();
            }
        };
        countDownTimer.start();
    }

    private void skipToTargetActivity(){
//        final boolean loginState = StorageManager.getInstance().getSharedPrefManager().getUserSharedPref().getLoginState();
//        if(loginState){
//            mvpView.skipToMain();
//        }else{
//            mvpView.skipToLogin();
//        }

        mvpView.skipToMain();
    }
}
