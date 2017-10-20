package com.mobcb.widget.model;

import android.graphics.drawable.Drawable;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/6/7 0007
 * @desc [PopupWindow菜单]
 */

public class PopMenu {
    /**
     * icon 的drawable对象
     */
    private Drawable drawable;
    /**
     * 菜单名称
     */
    private String name;

    public PopMenu(){}
    public PopMenu(String name){
        this.name = name;
    }
    public PopMenu(Drawable drawable,String name){
        this.drawable = drawable;
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
