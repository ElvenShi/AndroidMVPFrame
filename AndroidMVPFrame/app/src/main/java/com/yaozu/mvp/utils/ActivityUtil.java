package com.yaozu.mvp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yaozu.mvp.MyApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <操作activity的相关方法>
 *
 * @author : shiyaozu
 * @version : [v1.0.0, 2016/7/21]
 * @see [相关类/方法]
 * @since [V1]
 */
public class ActivityUtil {

    /**
     * 得到当前屏幕的bitmap
     *
     * @param rootView
     * @return
     */
    public static Bitmap getScreenBitmap(Activity activity, View rootView) {
        View viewScreen = rootView;
        int windowWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int windowHeight = activity.getWindow().getWindowManager().getDefaultDisplay().getHeight();
        // 获取状态栏高度
        Rect rect = new Rect();
        viewScreen.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        Matrix matrix = new Matrix();
        matrix.setScale(0.3f, 0.3f);
        if (windowHeight > rect.height() + rect.top) {
            windowHeight = rect.height() + rect.top;
        }
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, statusBarHeights, windowWidth, windowHeight - statusBarHeights, matrix, false);
        viewScreen.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark, @ColorRes int id) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(MyApplication.getContext().getResources().getColor(id));
                }
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean mIUISetStatusBarLightMode(Window window, boolean dark, @ColorRes int id) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(MyApplication.getContext().getResources().getColor(id));
                }
            } catch (Exception e) {

            }
        }
        return result;
    }


    /***
     * 解决快速点击
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime > 0 && time - lastClickTime < 500) {
            lastClickTime = time;
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 判断是否是连续点击
     *
     * @return
     * @creator liuzaibing
     */
    public synchronized static boolean isSeriesClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
