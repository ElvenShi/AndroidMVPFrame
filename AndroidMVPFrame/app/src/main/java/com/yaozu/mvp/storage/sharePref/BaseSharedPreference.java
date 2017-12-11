package com.yaozu.mvp.storage.sharePref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <SharedPref基础存储功能模块>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 *
 * @desc 该类中对于数据的存储方式包含同步和异步方法。如果需要根据数据存储结果进行下一步操作，则使用同步方法
 * 反之，则使用异步方法。建议优先考虑使用异步方法。
 */
public class BaseSharedPreference {
    private String fileName;

    private Context context;

    /**
     *
     * @param context
     *         this context must be the application context
     * @param fileName
     */
    public BaseSharedPreference(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    /**
     * Retrieve the package shared preferences object.
     *
     * @return
     */
    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     *
     * @param key
     * @param value
     */
    public boolean saveBoolean(String key, boolean value) {
        return getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    /**
     * Save the boolean value
     * @param key
     * @param value
     */
    public void asyncSaveBoolean(String key, boolean value){
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defvalue) {
        return getSharedPreferences().getBoolean(key, defvalue);
    }

    /**
     * This method is sync method
     * Save a string value to the shared preference.
     *
     * @param key   to mark the store value.
     * @param value to save value.
     */
    public boolean syncSaveString(String key, String value) {
        return getSharedPreferences().edit().putString(key, value).commit();
    }

    /**
     * This method is async method
     * Save a string value to the shared preference.
     * @param key
     * @param value
     */
    public void asyncSaveString(String key, String value){
        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * Get the specified value through the key value.
     *
     * @param key to retrieve the value.
     * @return the string value returned.
     */
    public String getString(String key, String def) {
        return getSharedPreferences().getString(key, def);
    }

    /**
     * Save a integer value to the shared preference.
     *
     * @param key   to mark the store value.
     * @param value to saved value.
     */
    public boolean syncSaveInt(String key, int value) {
        return getSharedPreferences().edit().putInt(key, value).commit();
    }

    /**
     * This method is async method
     * Save a integer value to the shared preference.
     * @param key
     * @param value
     */
    public void asyncSaveInt(String key, int value){
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    /**
     * Get the specified value through the key value.
     *
     * @param key to retrieve the value.
     * @return the integer value returned.
     */
    public int getInt(String key, int def) {
        return getSharedPreferences().getInt(key, def);
    }

    /**
     * Save a Long value to the shared preference.
     *
     * @param key   to mark the store value.
     * @param value to saved value.
     */
    public boolean syncSaveLong(String key, long value) {
        return getSharedPreferences().edit().putLong(key, value).commit();
    }

    /**
     * This method is a async method
     * Save a Long value to the shared preference.
     * @param key
     * @param value
     */
    public void asyncSaveLong(String key, long value){
        getSharedPreferences().edit().putLong(key, value).apply();
    }


    /**
     * Get the specified value through the key value.
     *
     * @param key to retrieve the value.
     * @return the integer value returned.
     */
    public long getLong(String key, long def) {
        return getSharedPreferences().getLong(key, def);
    }

    /**
     * 清除所有对应的fileName的sp缓存
     */
    public boolean syncClear() {
        return getSharedPreferences().edit().clear().commit();
    }

    /**
     * 异步清除数据
     */
    public void asyncClear(){
        getSharedPreferences().edit().clear().apply();
    }
}
