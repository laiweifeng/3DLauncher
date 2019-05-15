package com.laiweifeng.launcher.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.laiweifeng.launcher.R;


public class AppImageView extends RelativeLayout {

    private ImageView iv_app;
    private ImageView iv_status;

    public static final int DEFAULT=-1;
    public static final int TOPPING=0;
    public static final int DELETE=1;
    private int mStatus=DEFAULT;
    private OnAppClickListener onAppClickListener;
    private View rootview;
    private OnAppFocusChangeListener onAppFocusChangeListener;

    public AppImageView(Context context) {
        this(context,null);
    }

    public AppImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AppImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         View.inflate(context, R.layout.widget_app_image,this);
        rootview = findViewById(R.id.rootview);
        iv_app = findViewById(R.id.iv_app);
        iv_status = findViewById(R.id.iv_status);


        rootview. setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(onAppFocusChangeListener!=null){
                        Object tag = rootview.getTag();
                        int position=-1;
                        if(tag!=null){
                            position=(int)tag;
                        }
                        onAppFocusChangeListener.onFocusChange(position);
                    }

                    if(mStatus==TOPPING){
                        iv_status.setVisibility(View.VISIBLE);
                        iv_status.setImageResource(R.drawable.icon_dingzhi);
                    }else if(mStatus==DELETE){
                        iv_status.setVisibility(View.VISIBLE);
                        iv_status.setImageResource(R.drawable.delete_icon);
                    }else{
                        iv_status.setVisibility(View.GONE);
                    }
                    zoomInView(AppImageView.this);
                }else{
                    iv_status.setVisibility(View.GONE);
                    zoomOutView(AppImageView.this);
                }

            }
        });

        rootview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAppClickListener!=null){
                    Object tag = rootview.getTag();
                    int position=-1;
                    if(tag!=null){
                        position=(int)tag;
                    }
                    onAppClickListener.onAppClick(position);
                }
            }
        });

    }

    private void zoomInView(View v){
        AnimatorSet animSet = new AnimatorSet();
        float[] values = new float[] { 1.0f  ,1.18f  };
        animSet.playTogether(ObjectAnimator.ofFloat(v, "scaleX", values),
                ObjectAnimator.ofFloat(v, "scaleY", values));
        animSet.setDuration(300).start();
    }

    private void zoomOutView(View v){
        AnimatorSet animSet = new AnimatorSet();
        float[] values = new float[] { 1.18f  ,1.0f  };
        animSet.playTogether(ObjectAnimator.ofFloat(v, "scaleX", values),
                ObjectAnimator.ofFloat(v, "scaleY", values));
        animSet.setDuration(300).start();
    }


    public void setAppIcon(String iconPath){
        Bitmap bitmap = BitmapFactory.decodeFile(iconPath);
        iv_app.setImageBitmap(bitmap);
    }
    public void setPosition(int position){
        rootview.setTag(position);
    }

    public void setStatus(int status){
        this.mStatus=status;
    }

    public void setOnAppClickListener(OnAppClickListener listener){
        this.onAppClickListener=listener;
    }

    public interface OnAppClickListener{
        void onAppClick(int position);
    }
    public void setOnAppFocusChangeListener(OnAppFocusChangeListener listener){
        this.onAppFocusChangeListener=listener;
    }

    public interface OnAppFocusChangeListener{
        void onFocusChange(int position);
    }
}
