package com.yaozu.mvp.utils.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.Serializable;

/**
 * @version :V1.0.0
 * @描述 ：
 * @user ：shiyaozu
 * @date 创建时间 ： 2016/3/22
 */
public class PackageUtil implements Serializable {
    /**
     * Package单例
     */
    private static PackageUtil instance;

    /**
     * 获取Package单例
     *
     * @return
     */
    public static PackageUtil getInstance() {
        if (instance == null) {
            instance = new PackageUtil();
        }
        return instance;
    }

    /**
     * 包信息实例
     */
    private PackageInfo info = null;
    /**
     * 包管理器实例
     */
    private PackageManager pm;

    public void init(Context context) {
        pm = context.getPackageManager();
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取应用版本CODE
     *
     * @return
     */
    public int getLocalVersionCode() {
        return info != null ? info.versionCode : 0;
    }

    /**
     * 获取应用版本名称
     *
     * @return
     */
    public String getLocalVersionName() {
        return info != null ? info.versionName : "defaultVName";
    }

    /**
     * 获取应用名称
     *
     * @return
     */
    public String getAppName() {
        return info != null ? (String) info.applicationInfo.loadLabel(pm) : "defaultAppName";
    }

    /**
     * 获取包名
     *
     * @return
     */
    public String getPackageName() {
        return info != null ? info.packageName : "defaultPackageName";
    }
}
