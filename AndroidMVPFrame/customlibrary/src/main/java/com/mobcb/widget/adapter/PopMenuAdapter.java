package com.mobcb.widget.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobcb.refreshlayout.R;
import com.mobcb.widget.model.PopMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/6/7 0007
 * @desc [描述相关类/方法]
 */

public class PopMenuAdapter extends BaseAdapter {

    private List<PopMenu> menus;
    private LayoutInflater mInflater;
    public PopMenuAdapter(Context context,List<PopMenu>menus){
        mInflater = LayoutInflater.from(context);
        this.menus = (menus != null?menus:new ArrayList<PopMenu>());
    }

    @Override
    public int getCount() {
        return this.menus.size();
    }

    @Override
    public Object getItem(int position) {
        return this.menus.isEmpty()?null:this.menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.popup_menu_item_layout,parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!menus.isEmpty()){
            setData(holder,position);
        }
        return convertView;
    }

    private void setData(ViewHolder holder,int position){
        holder.textView.setText(menus.get(position).getName());
        Drawable drawable = menus.get(position).getDrawable();
        if (drawable != null){ // 说明有icon
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.textView.setCompoundDrawables(drawable,null,null,null);
        }
    }

    private static class ViewHolder{
        TextView textView;
    }
}
