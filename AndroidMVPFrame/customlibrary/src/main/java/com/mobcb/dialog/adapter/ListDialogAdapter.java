package com.mobcb.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobcb.refreshlayout.R;

import java.util.List;

public class ListDialogAdapter extends BaseAdapter {
    /**
     * 数据源
     */
    private List<String> mList;

    private Context context;

    public ListDialogAdapter(Context context,List<String> mList){
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_tm_list_dialog, null);
        TextView textView = (TextView) convertView.findViewById(R.id.list_dialog_tv);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_dialog_iv);
        View divider = convertView.findViewById(R.id.list_dialog_divider);
        imageView.setVisibility(View.GONE);
        textView.setText(getItem(position));
            /*if (selectedPosition == position) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }*/
        if (position < getCount() - 1) {
            divider.setVisibility(View.VISIBLE);
        } else {
            divider.setVisibility(View.GONE);
        }

        return convertView;
    }
}