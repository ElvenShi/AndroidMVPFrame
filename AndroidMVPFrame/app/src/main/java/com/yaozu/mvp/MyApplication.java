package com.yaozu.mvp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.wanjian.cockroach.Cockroach;
import com.yaozu.base.library.ApplicationContext;
import com.yaozu.base.library.okhttp.OkHttpUtils;
import com.yaozu.base.library.utils.DeviceUtil;
import com.yaozu.base.library.utils.ProcessUtils;
import com.yaozu.mvp.storage.StorageManager;
import com.yaozu.mvp.utils.crash.CrashHandler;
import com.yaozu.mvp.utils.log.AndroidLogAdapter;
import com.yaozu.mvp.utils.log.LogLevel;
import com.yaozu.mvp.utils.log.Logger;

import static android.content.ContentValues.TAG;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/17 0017
 * @desc : [相关类/方法]
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    /**
     * 当前activity名称
     */
    public static String currentActivityName = "";

    /**
     * 是否经历过loading
     */
    private static boolean hasLoading = true;

    public static Context getContext() {
        return instance;
    }

    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";


    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMainProcess(this)) {
            instance = this;
            // 将app context 传递给baselibrary
            ApplicationContext.getInstance().initContext(this);
            // 初始化OkHttp配置
            OkHttpUtils.initClient(this);
            initLogger();
        }
    }

    /**
     * 降低Android非必要crash
     */
    private void installCockroach(){
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
                            Toast.makeText(MyApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(),
                                    Toast.LENGTH_SHORT).show();
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    public static boolean isHasLoading() {
        return hasLoading;
    }

    public static void setHasLoading(boolean hasLoading) {
        MyApplication.hasLoading = hasLoading;
    }

    /**
     * 初始化数据(存在写操作,23以上需要动态申请权限，所以需要在权限申请完成后才能初始化)
     */
    public void initPermissionData() {
        //设备信息
        DeviceUtil.getInstance().initDevice(this, BuildConfig.VERSION_NAME, BuildConfig.FLAVOR);
        //异常crash处理
        CrashHandler.getInstance().init(this);
        // 初始化储存管理器
        StorageManager.getInstance().init(this);
    }

    /**
     * 初始化日志配置
     */
    private void initLogger(){
        if (BuildConfig.DEBUG){
            Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(2)                // default 0
                    .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
        }else {
            Logger.init().logLevel(LogLevel.NONE);//关闭日志打印
        }
    }

    /**
     * 启用严格模式
     *
     * @Creator shiyaozu
     */
    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectAll().
                    penaltyLog().
                    build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().
                    detectAll().
                    penaltyLog().
                    build());
        }
    }
}
