package com.yaozu.base.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/10/18 0018
 * @desc : [格式规范,匹配]
 * 1、是否是email
 * 2、是否是手机号码
 * 3、是否是邮编
 * 4、是否是价格
 */

public class MatchUtils {
    /**
     * 验证是否符合email规范
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(strEmail);
        return matcher.matches();
    }

    /**
     * 是否符合手机号码规范
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern
                .compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 是否符合价格规范
     *
     * @param price
     * @return
     */
    public static boolean isPrice(String price) {
        String aaString = "^(?!0\\d)(?!\\.)[0-9]+(\\.[0-9]{1,2})?$";
        Pattern p = Pattern.compile(aaString);
        Matcher m = p.matcher(price);
        return m.matches();
    }

    /**
     * <邮编判断>
     * <p>
     * <功能详细描述>
     *
     * @param post
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPost(String post) {
        String patrn = "^[1-9][0-9]{5}$";
        Pattern p = Pattern.compile(patrn);
        Matcher m = p.matcher(post);
        return m.matches();
    }
}
