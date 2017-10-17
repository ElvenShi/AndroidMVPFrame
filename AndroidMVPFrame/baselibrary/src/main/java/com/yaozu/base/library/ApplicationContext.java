package com.yaozu.base.library;

import android.app.Application;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/17 0017
 * @desc : [相关类/方法]
 */

public class ApplicationContext {
    private Application context;
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public void initContext(Application context) {
        if (context == null){
            return;
        }
        this.context = context;
    }

    public Application getContext() {
        return context;
    }
}
