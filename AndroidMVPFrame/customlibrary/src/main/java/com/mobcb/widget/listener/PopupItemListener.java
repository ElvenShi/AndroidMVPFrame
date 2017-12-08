package com.mobcb.widget.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/6/7 0007
 * @desc [PopupWindow点击事件监听器]
 */

public interface PopupItemListener {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
