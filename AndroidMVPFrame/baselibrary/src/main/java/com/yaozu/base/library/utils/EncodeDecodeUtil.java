package com.yaozu.base.library.utils;


import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/18 0018
 * @desc : [字符串编解码]
 */
public class EncodeDecodeUtil {
    /**
     * 编成utf-8字符串
     * @param content
     * @return
     */
    public static String toEncodeUTF8(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * utf-8解码
     * @param content
     * @return
     */
    public static String toDecodeUTF8(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
