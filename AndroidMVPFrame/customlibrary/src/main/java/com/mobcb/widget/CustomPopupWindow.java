package com.mobcb.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mobcb.refreshlayout.R;
import com.mobcb.widget.adapter.PopMenuAdapter;
import com.mobcb.widget.listener.PopupItemListener;
import com.mobcb.widget.model.PopMenu;

import java.util.List;


/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/6/7 0007
 * @desc [自定义PopupWindow]
 */

public class CustomPopupWindow extends PopupWindow {

    public static final int POPUP_WINDOW_LEFT = 0;
    public static final int POPUP_WINDOW_UP = 1;
    public static final int POPUP_WINDOW_RIGHT = 2;
    public static final int POPUP_WINDOW_DOWN = 3;

    private View contentView;

    private ListView mListView;

    private PopMenuAdapter popMenuAdapter;

    private PopupItemListener listener;

    private View rootView;

    /**
     * 与屏幕等宽，高度包裹内容的PopupWindow
     * @param context
     * @param menuList
     */
    public CustomPopupWindow(Context context, List<PopMenu> menuList,int width,int height){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_menu_layout, null);
        rootView = contentView.findViewById(R.id.menu_layout);
        this.setContentView(contentView);
        // 设置弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(width);

        // 设置弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(height);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);//点击别处可消失
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        mListView = (ListView) contentView.findViewById(R.id.menu_list);
        popMenuAdapter = new PopMenuAdapter(context,menuList);
        mListView.setAdapter(popMenuAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null){
                    listener.onItemClick(parent,view,position,id);
                }
                CustomPopupWindow.this.dismiss();//关闭窗口
            }
        });

    }

    /**
     * 设置分割线高度
     * @param height
     */
    public void setDividerHeight(int height){
        mListView.setDividerHeight(height);
    }

    /**
     * 设置分割线高度和颜色。
     * ListView有个奇怪的地方，通过setDivider(Drawable)设置颜色的时候，设置完必须要设置一下高度；
     * 否则，设置的颜色是无法正常显示的，同时高度也消失了,即使前面设置了高度值。具体可以查看ListView源码.
     * 所以，为了防止出现上述情况，这里干脆在设置分割线颜色的同时设置一下高度。
     * @param divider
     */
    public void setDividerColorAndHeight(Drawable divider,int height){
        mListView.setDivider(divider);
        mListView.setDividerHeight(height);
    }

    /**
     * 设置背景
     * @param drawableId
     */
    public void setBackground(int drawableId){
        rootView.setBackgroundResource(drawableId);
    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setListener(PopupItemListener listener){
        this.listener = listener;
    }


    /**
     * 显示popupWindow
     * @param anchorView 以哪一个view为锚点
     * @param offX 横坐标偏移量
     * @param offY 纵坐标偏移量
     */
    public void showPopupWindow(View anchorView,int offX,int offY){
        if (!this.isShowing()) {
            this.showAsDropDown(anchorView, offX, offY);
        } else {
            this.dismiss();
        }
    }
}
