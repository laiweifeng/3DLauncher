package com.laiweifeng.launcher.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laiweifeng.launcher.R;


public class LoadStatusView extends RelativeLayout {

    private Button btn_reload;
    private onReloadClickListener reloadClickListener;
    private ImageView iv_statusPicture;
    private TextView tv_message;
    private AnimationDrawable mAnimationDrawable;

    private int HIDE=0;
    private int RELOAD=1;
    private int LOADING=2;
    private int NO_DATA=3;
    private int mCurrentStatus=HIDE;

    ObjectAnimator rotation;
    public LoadStatusView(Context context) {
        this(context,null);
    }

    public LoadStatusView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.widget_load_status, this);
        btn_reload = view.findViewById(R.id.btn_reload);
        tv_message = view.findViewById(R.id.tv_message);
        iv_statusPicture = view.findViewById(R.id.iv_statusPicture);
        btn_reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reloadClickListener !=null){
                    reloadClickListener.OnReloadClick();
                }
            }
        });

//        mAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.loading_anim);
//        iv_statusPicture.setBackgroundDrawable(mAnimationDrawable);
//        mAnimationDrawable.start();

        if(rotation==null){
            rotation = ObjectAnimator.ofFloat(iv_statusPicture, "rotation", 0, 360);
            rotation.setInterpolator(new LinearInterpolator());
            rotation.setDuration(4000);
            rotation.setRepeatCount(-1);
        }

        hideLoadStatus();
    }


    public void hideLoadStatus(){
//        mAnimationDrawable.stop();
        rotation.cancel();
        if(mCurrentStatus==NO_DATA){
            return;
        }
        mCurrentStatus=HIDE;
       setVisibility(View.GONE);
    }
//    public void showReload(){
//        btn_reload.setVisibility(View.VISIBLE);
//        mAnimationDrawable.stop();
//        iv_statusPicture.setBackgroundResource(R.drawable.weibosdk_empty_failed);
//        tv_message.setText(R.string.load_error);
//        setVisibility(View.VISIBLE);
//        mCurrentStatus=RELOAD;
//    }

    public void showLoading(){
        rotation.start();
        btn_reload.setVisibility(View.GONE);
        iv_statusPicture.setBackgroundResource(R.mipmap.loading_icon);
//        mAnimationDrawable.start();
//        tv_message.setText(R.string.loading_tip);
        setVisibility(View.VISIBLE);
        mCurrentStatus=LOADING;
    }

//    public void showNoData(){
//        btn_reload.setVisibility(View.GONE);
//        mAnimationDrawable.stop();
//        iv_statusPicture.setBackgroundResource(R.drawable.no_data_error);
//        tv_message.setText(R.string.no_data);
//        setVisibility(View.VISIBLE);
//        mCurrentStatus=NO_DATA;
//    }



    public void setonReloadClickListener(onReloadClickListener listener){
        this.reloadClickListener =listener;
    }

    public  interface onReloadClickListener {
        void OnReloadClick();
    }


}
