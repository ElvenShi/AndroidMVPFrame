package com.yaozu.base.library;

import android.content.Context;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/17 0017
 * @desc : [相关类/方法]
 */

public class ApplicationContext {
    private Context context;
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public void initContext(Context context) {
        if (context == null){
            return;
        }
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
