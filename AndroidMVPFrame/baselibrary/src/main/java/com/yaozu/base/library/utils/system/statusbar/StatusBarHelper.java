package com.yaozu.base.library.utils.system.statusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/17 0017
 * @desc : [适配4.4以上版本 MIUI6、Flyme 和其他 Android6.0 及以上版本状态栏字体颜色]
 */

public class StatusBarHelper {

    public static final int MIUI = 1;
    public static final int FLYME = 2;
    public static final int ANDROID_M = 3;
    public static final int OTHER = 4;

    @IntDef({MIUI, FLYME, ANDROID_M, OTHER})
    @Retention(RetentionPolicy.SOURCE)
    @interface SystemType {
    }

    public static int statusBarLightMode(Activity activity) {
        return statusMode(activity, true);
    }

    public static int statusBarDarkMode(Activity activity) {
        return statusMode(activity, false);
    }

    private static int statusMode(Activity activity, boolean isFontColorDark) {
        @SystemType int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = MIUI;
            } else if (new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = FLYME;
            }
//            else if (new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark)) {
//                result = ANDROID_M;
//            }
        }
        return result;
    }

    public static void statusBarLightMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, true);
    }

    public static void statusBarDarkMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, false);
    }

    private static void statusBarMode(Activity activity, @SystemType int type, boolean isFontColorDark) {
        if (type == MIUI) {
            new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark);
        } else if (type == FLYME) {
            new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark);
        }
//        else if (type == ANDROID_M) {
//            new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark);
//        }
    }

    /**
     * 获得状态栏的高度，通过反射
     *
     * @param context
     * @return 如果为－1则获取失败
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return statusHeight;
    }
}
