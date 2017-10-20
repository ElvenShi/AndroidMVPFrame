package com.yaozu.mvp.contract;

import com.yaozu.mvp.contract.base.BasePresenter;
import com.yaozu.mvp.contract.base.BaseView;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/20 0020
 * @desc : [相关类/方法]
 */

public interface HomeContract {

    interface Presenter extends BasePresenter<View> {
        void initData();
    }

    interface View extends BaseView {
//        void toMainActivity();
    }
}
