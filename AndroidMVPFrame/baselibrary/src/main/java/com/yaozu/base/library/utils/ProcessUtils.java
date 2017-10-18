package com.yaozu.base.library.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/17 0017
 * @desc : [进程相关的工具类]
 */

public class ProcessUtils {

    public ProcessUtils(){
        throw new UnsupportedOperationException("not support operation");
    }
    /**
     * 判断当前是否是主进程
     *
     * @return
     */
    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : am
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
