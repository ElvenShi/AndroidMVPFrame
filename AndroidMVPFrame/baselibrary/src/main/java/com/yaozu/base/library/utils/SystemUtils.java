package com.yaozu.base.library.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Create By SYZ
 * <br><br>
 * 该类中主要是一些常用的工具方法<br><br>
 * 获取屏幕宽度<br>
 * 获取屏幕密度(dpi)<br>
 * 获取屏幕高度<br>
 * sd卡是否挂载并可读写<br>
 * 获取mac地址<br>
 * 获取本机ip地址<br>
 * 添加输入框输入长度限制<br>
 * 获取当前CPU类型<br>
 * 是否是主线程<br>
 */
public class SystemUtils {

    /**
     * 计算视图的宽高。
     *
     * @param view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int nWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int nHeight = p.height;
        int nHeightSpec;
        if (nHeight > 0) {
            nHeightSpec = MeasureSpec.makeMeasureSpec(nHeight,
                    MeasureSpec.EXACTLY);
        } else {
            nHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        view.measure(nWidthSpec, nHeightSpec);
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /***
     * 获取屏幕密度
     * @return
     */
    public static int getScreenDpi(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * sd卡是否挂载并可读写
     *
     * @return
     */
    public static boolean isExternalStorageMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 获取mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        final WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取本机ip地址
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 添加输入框输入长度限制
     * @param editText
     * @param nLengthLimit
     */
    public static void addEditTextLengthFilter(EditText editText,
                                               int nLengthLimit) {
        InputFilter filters[] = editText.getFilters();
        if (filters == null) {
            editText.getEditableText().setFilters(
                    new InputFilter[]{new InputFilter.LengthFilter(
                            nLengthLimit)});
        } else {
            final int nSize = filters.length + 1;
            InputFilter newFilters[] = new InputFilter[nSize];
            int nIndex = 0;
            for (InputFilter filter : filters) {
                newFilters[nIndex++] = filter;
            }
            newFilters[nIndex] = new InputFilter.LengthFilter(nLengthLimit);
            editText.getEditableText().setFilters(newFilters);
        }
    }

    /**
     * 验证当前CPU类型是否是armV7
     *
     * @return
     */
    public static boolean isArmV7() {
        return getCpuType().contains("armv7");
    }

    /**
     * 获取当前CPU类型
     *
     * @return
     */
    public static String getCpuType() {
        String strInfo = getCpuString();
        String strType = null;

        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("ARMv8")) {
            strType = "armv8";
        } else if (strInfo.contains("x86")) {
            strType = "x86";
        } else {
            strType = strInfo;
            return strType;
        }

        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }
        return strType;
    }

    static public String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "x86";
        }

        String strInfo = "";
        RandomAccessFile reader = null;
        try {
            byte[] bs = new byte[1024];
            reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }

        return strInfo;
    }

    /**
     * 是否是主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
