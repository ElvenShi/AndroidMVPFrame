package com.mobcb.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcb.refreshlayout.R;



/**
 * <提示公共类>
 *
 * @author shiyaozu
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public class ToastUtil {

    public ToastUtil() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     * 中间显示Toast
     *
     * @param context
     * @param msg     显示内容
     */
    public static void showCenterToast(Context context, String msg) {
        View focusToastView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        ((TextView) focusToastView.findViewById(R.id.toast_msg_tv)).setText(msg);
        Toast toast = new Toast(context);
        toast.setView(focusToastView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 底部显示Toast
     *
     * @param context
     * @param msg
     */
    public static void showBottomToast(Context context, String msg) {
        View focusToastView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        ((TextView) focusToastView.findViewById(R.id.toast_msg_tv)).setText(msg);
        Toast toast = new Toast(context);
        toast.setView(focusToastView);
        toast.show();
    }

    /**
     * 显示带对勾的toast
     *
     * @param context
     * @param id      显示内容
     */
    public static void showCenterToast(Context context, int id) {
        showCenterToast(context, context.getString(id));
    }

    /**
     * 底部显示Toast
     *
     * @param context
     * @param id
     */
    public static void showBottomToast(Context context, int id) {
        showBottomToast(context, context.getString(id));
    }


}
