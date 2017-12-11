package com.yaozu.mvp.storage.sharePref.sharePrefItem;

import android.content.Context;

import com.yaozu.mvp.jni.SFJniUtils;
import com.yaozu.mvp.storage.sharePref.BaseSharedPreference;




/**
 * <用户信息缓存>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserInfoSharedPref extends BaseSharedPreference {

    /**
     * 用户id
     */
    public static final String USER_ID_KEY = "userId";

    /**
     * 登录名
     */
    public static final String USER_NAME_KEY = "username";

    /**
     * 密码
     */
    public static final String USER_PASSWORD_KEY = "encrypt_password";

    /**
     * 登录状态  是否登录
     */
    public static final String LOGIN_STATE_KEY = "login_state";

    /**
     * 用户基本信息,用户存放用户实体类
     */
    public static final String USER_INFO = "login_data";

    /**
     * 用户与云端通信token
     */
    public static final String TOKEN = "token";


    public UserInfoSharedPref(Context context, String fileName) {
        super(context, fileName);
    }

    public String getUserName() {
        return getString(USER_NAME_KEY, "");
    }

    public void setUserName(String userName) {
        asyncSaveString(USER_NAME_KEY, userName);
    }

//    public LoginData getLoginData() {
//        String userBaseStr = getString(USER_INFO, "");
//        if (TextUtils.isEmpty(userBaseStr)) {
//            return null;
//        }
//        try {
//            return GsonHelper.toType(userBaseStr, LoginData.class);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public void setLoginData(LoginData loginData) {
//        if (loginData == null) {
//            saveString(USER_INFO, "");
//            return;
//        }
//        String userBaseStr = GsonHelper.toJson(loginData);
//        saveString(USER_INFO, userBaseStr);
//    }


    public String getUserId() {
        return getString(USER_ID_KEY, "");
    }

    public void setUserId(String userId) {
        asyncSaveString(USER_ID_KEY, userId);
    }

    public String getToken() {
        return getString(TOKEN, "");
    }

    public void setToken(String eduToken) {
        asyncSaveString(TOKEN, eduToken);
    }


    public void setPassword(String password) {
        try {
            password = SFJniUtils.encryptPsd(password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        asyncSaveString(USER_PASSWORD_KEY, password);
    }

    public String getPassword() {
        String psd = getString(USER_PASSWORD_KEY, "");
        try {
            psd = SFJniUtils.decryptPsd(psd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return psd;
    }

    public void setLoginState(boolean state) {
        asyncSaveBoolean(LOGIN_STATE_KEY, state);
    }

    public boolean getLoginState() {
        return getBoolean(LOGIN_STATE_KEY, false);
    }
}
