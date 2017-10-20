package com.mobcb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobcb.dialog.adapter.ListDialogAdapter;
import com.mobcb.refreshlayout.R;

import java.util.List;

/**
 * <页面基础公共功能实现>
 *
 * @author shiyaozu
 * @version [版本号, 2017/2/13]
 * @see [相关类/方法]
 * @since [V1]
 * @desc 类似iOS的底部弹出型dialog
 *
 * e.g.
 *      List<String> items = new ArrayList<>();
 *      items.add("拍照");
 *      items.add("从相册选取");
 *      BottomSelectListDialog dialog = new BottomSelectListDialog(this,items);
 *      dialog.show();
 *      dialog.setOnItemSelectedListener(new BottomSelectListDialog.OnItemSelectListener() {
 *          @Override
 *          public void onItemSelected(int position) {
 *              switch (position){
 *                case 0: // 拍照
 *                    break;
 *                case 1: //从相册选取
 *                    break;
 *                  }
 *          }
 *      });
 */

public class BottomSelectListDialog extends Dialog {
    private Context context;
    /**
     * 数据源
     */
    private List<String> mList;
    /**
     * listview容器
     */
    private ListView lvContainer;
    /**
     * 取消按钮
     */
    private TextView tvCancel;
    /**
     * 记录当前被选中的位置
     */
    private int selectedPosition;
    /**
     * 点击监听
     */
    private OnItemSelectListener listener;

    public BottomSelectListDialog(Context context, List<String> list) {
        super(context, R.style.FilterDialogStyle);
        this.context = context;
        this.mList = list;
        // dialog 停留在底部，并且全屏
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_select_list_dialog);
        lvContainer = (ListView) findViewById(R.id.lv_data_container);
        tvCancel = (TextView) findViewById(R.id.tv_sf_list_dialog_cancel);
        //点击取消按钮，dialog消失
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        initView();
    }

    /**
     * 设置item字体颜色
     * @param color
     */
    public void setItemColor(int color){

    }

    /**
     * 根据传入的数据源mlist绘制listview
     */
    private void initView() {
        ListDialogAdapter mAdapter = new ListDialogAdapter(context,mList);
        lvContainer.setAdapter(mAdapter);
        //listview点击监听
        lvContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;  //记录下当前选中位置，在adapter中根据选定位置来控制小勾是否展显示
                if (null != listener) {
                    listener.onItemSelected(position);  //触发点击事件，在activity中回调
                }
                dismiss();
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
