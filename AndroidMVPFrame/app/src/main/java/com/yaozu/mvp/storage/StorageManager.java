package com.yaozu.mvp.storage;

import android.content.Context;

import com.yaozu.mvp.storage.localstorage.LocalFileDirManager;
import com.yaozu.mvp.storage.sharePref.SharedPrefManager;


/**
 * @author : Shiyaozu
 * @version : V1.0
 * @date : 2017/5/4
 * @Desc: 该类是数据存储，获取，清除相关的管理器。
 * 该类包含初始化SharedPreference 一些必要数据。所以该类要在Application onCreate方法中初始化
 */

public class StorageManager {
    private static final StorageManager INSTANCE = new StorageManager();
    private StorageManager(){}
    public static StorageManager getInstance(){
        return INSTANCE;
    }

    /**
     * 初始化数据
     * @param context this context must be application context
     */
    public void init(Context context){
        LocalFileDirManager.getInstance().initOnApplicationCreate(context);
        SharedPrefManager.getInstance().initOnApplicationCreate(context);
    }

    /**
     * 获取SharedPrefManager事例
     * @return
     */
    public SharedPrefManager getSharedPrefManager(){
        return SharedPrefManager.getInstance();
    }

    /**
     * 获取本地文件目录LocalFileDirManager事例
     * @return
     */
    public LocalFileDirManager getLocalFileStorageManager(){
        return LocalFileDirManager.getInstance();
    }

}
