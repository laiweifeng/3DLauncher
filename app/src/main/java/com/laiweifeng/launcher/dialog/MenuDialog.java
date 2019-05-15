package com.laiweifeng.launcher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.laiweifeng.launcher.R;


public class MenuDialog extends Dialog implements View.OnClickListener {

    public static final int TOPPING =0 ;
    public static final int DELETE_APP =1 ;
    private View rootview_topping;
    private View rootview_delete;
    private OnMenuItemClickListener onMenuItemClickListener;

    public MenuDialog(@NonNull Context context) {
        super(context, R.style.Transparent);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogBottom); // 添加动画
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu);
        rootview_topping = findViewById(R.id.rootview_topping);
        rootview_delete = findViewById(R.id.rootview_delete);
        rootview_topping.setOnClickListener(this);
        rootview_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onMenuItemClickListener==null){
            return;
        }
        switch (v.getId()){
            case R.id.rootview_topping:
                onMenuItemClickListener.onItemClick(TOPPING);
                break;
            case R.id.rootview_delete:
                onMenuItemClickListener.onItemClick(DELETE_APP);
                break;
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
        this.onMenuItemClickListener=listener;
    }

    public interface OnMenuItemClickListener{
        void onItemClick(int position);
    }
}
