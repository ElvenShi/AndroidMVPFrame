package com.yaozu.mvp.utils;

import com.alibaba.fastjson.JSONObject;
import com.yaozu.base.library.utils.GeneralUtils;
import com.yaozu.mvp.utils.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * <json公共解析库>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public class GsonHelper {

    private static String TAG = GsonHelper.class.getName();

    /**
     * 把json string 转化成类对象
     *
     * @param str
     * @param t
     * @return
     */
    public static <T> T toType(String str, Class<T> t) {
        try {
            if (str != null && !"".equals(str.trim())) {
                T res = JSONObject.parseObject(str, t);
                return res;
            }
        } catch (Exception e) {
            Logger.e("exception:" + e.getMessage());
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("{list:");
                sb.append(str);
                sb.append("}");
                T res = JSONObject.parseObject(sb.toString(), t);
                return res;
            } catch (Exception e1) {
                Logger.e("exception:" + e1.getMessage());
            }
        }
        return null;
    }

    /**
     * 把json string 转化成类对象
     *
     * @param t
     * @return
     */
    public static <T> T toType(Object obj, Class<T> t) {
        return toType(toJson(obj), t);
    }

    /**
     * 将json string 转化为list集合  注意  不要解析大量数据
     *
     * @param jsonStr
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> toArray(String jsonStr, Class<T> t) {
        if (!GeneralUtils.isNullOrEmpty(jsonStr)) {
            return JSONObject.parseArray(jsonStr, t);
        }
        return new ArrayList<>();
    }

    /**
     * 把类对象转化成json string
     *
     * @param t
     * @return
     */
    public static <T> String toJson(T t) {
        return JSONObject.toJSONString(t);
    }

}
