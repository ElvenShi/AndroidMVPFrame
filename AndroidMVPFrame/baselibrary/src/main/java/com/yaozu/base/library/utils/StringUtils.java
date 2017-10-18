package com.yaozu.base.library.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * <通用工具类>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 *
 * isNull - 判断对象是否为null
 * isNullToEmpty - 判断String为空则返回""
 * isNotNull - 判断对象是否不为null
 * isNull(Object... objs) - 判断集合对象是否为null  添加顺序依次遍历
 * isNotNull(Object... objs) - 判断集合对象是否不为null  添加顺序依次遍历
 * isNullOrEmpty - 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
 * isNotNullOrEmpty - 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
 * isEmail(String email) - <邮箱判断>
 * isTel - 手机号码判断
 * isPost - 邮编
 */
public final class StringUtils {

    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return (null == obj) ? true : false;
    }

    /**
     * 判断String为空则返回""
     *
     * @param obj
     * @return
     */
    public static String isNullToEmpty(String obj) {
        if (isNull(obj)) return "";
        return obj;
    }


    /**
     * 判断对象是否为null , 为null返回false,否则返回true
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断集合对象是否为null  添加顺序依次遍历
     *
     * @param objs
     * @return
     */
    public static boolean isNull(Object... objs) {
        for (Object obj :objs) {
            if (isNull(obj))
                return true;
        }
        return false;
    }

    /**
     * 判断集合对象是否为null 按添加顺序依次遍历
     *
     * @param objs
     * @return
     */
    public static boolean isNotNull(Object... objs) {
        for (Object obj :objs) {
            if (isNull(obj))
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrEmpty(String str) {
        return (null == str || "".equals(str.trim()));
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * http://stackoverflow.com/questions/3495890/how-can-i-put-a-listview-into-a-scrollview-without-it-collapsing/3495908#3495908
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildrenExtend(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // 去除textview的排版问题

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}