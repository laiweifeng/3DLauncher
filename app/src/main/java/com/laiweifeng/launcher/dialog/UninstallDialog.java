package com.laiweifeng.launcher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.laiweifeng.launcher.R;


public class UninstallDialog extends Dialog implements View.OnClickListener {

    private OnOkClickListener onOkClickListener;
    private View rootview_buttons;
    private View loading;

    public UninstallDialog(@NonNull Context context) {
        super(context, R.style.Transparent);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogBottom); // 添加动画
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_uninstall);
        View btn_ok = findViewById(R.id.btn_ok);
        View btn_cancel = findViewById(R.id.btn_cancel);
        rootview_buttons = findViewById(R.id.rootview_buttons);
        loading = findViewById(R.id.loading);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                if(onOkClickListener !=null){
                    onOkClickListener.onClick();
                }
                break;

            case R.id.btn_cancel:
                dismiss();
                break;
        }

    }

    public void showLoading(){
        rootview_buttons.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    public void setOnOkClickListener(OnOkClickListener listener){
        this.onOkClickListener =listener;
    }

    public interface OnOkClickListener{
        void onClick();
    }
}
