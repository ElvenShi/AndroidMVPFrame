package com.yaozu.mvp.storage.sharePref;

import android.content.Context;

import com.yaozu.mvp.storage.sharePref.sharePrefItem.UserInfoSharedPref;




/**
 * <管理SharedPreference存储、读取>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 *
 * @desc 该类是shared preference 管理类。用于处理sharedPreference存储和读取数据
 */
public class SharedPrefManager {

    private Context mApplicationContext;

    /**
     * 用于存放用户基本信息的shared preference 文件名
     * 用户相关信息:帐号，(加密后的)密码,userId,登录状态，用户基本信息
     * @see UserInfoSharedPref
     */
    private static final String USER_INFO_SHARED_PREF = "user_info_shared_pref";

    /**
     * 用户信息缓存
     */
    private UserInfoSharedPref mUserSharedPref = null;


    private static final SharedPrefManager INSTANCE = new SharedPrefManager();

    private SharedPrefManager(){}

    public static SharedPrefManager getInstance(){
        return INSTANCE;
    }

    /**
     * 使用SharedPrefManger之前需要在Application中调用方法初始化context
     * @param context
     */
    public void initOnApplicationCreate(Context context) {
        mApplicationContext = context;
    }

    public void clearOnApplicationQuit() {
        mUserSharedPref = null;
        mApplicationContext = null;
    }

    /**
     * 用户信息缓存
     *
     * @return
     */
    public UserInfoSharedPref getUserSharedPref() {
        return mUserSharedPref == null ? mUserSharedPref = new UserInfoSharedPref(
                mApplicationContext, USER_INFO_SHARED_PREF) : mUserSharedPref;
    }


    /**
     * 退出之后，清除与用户相关的信息
     */
    public void clear() {
        if (mUserSharedPref != null){
            mUserSharedPref.asyncClear();
        }
    }

    /**
     * 用于主进程挂了之后的清除用户sp信息
     * 退出之后，清除与用户相关的信息
     */
    public void clear(SharedPrefManager sharedPrefManager) {

    }

}
