package com.yaozu.mvp.func;

import android.content.Context;
import android.view.View;

/**
 * <公共方法抽象>
 *
 * @author shiyaozu
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [V1]
 */
public interface CreateInit {

    /**
     * 初始化布局组件
     */
    public void initViews(Context context, View root);

    /**
     * 增加按钮点击事件
     */
    void initListeners();

    /**
     * 初始化数据
     */
    public void initData();

    /**
     * @return 布局ID
     */
    public int getLayoutId();

    /**
     * 通用toolbar 的Id
     * @return
     */
    public int getToolBarId();

    /**
     * 返回当前页面的名称 用于统计分析
     */
    String getName();

    /**
     * 返回当前activity是否是包含fragment的activity
     * @return
     */
    boolean isFragmentActivity();


//    /**
//     * 5.0以上，设置statusbar的颜色
//     *
//     * @return colorid
//     */
//    public int setStatusBarColor();
}

