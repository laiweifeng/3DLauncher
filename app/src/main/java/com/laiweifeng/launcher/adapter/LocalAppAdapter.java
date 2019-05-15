package com.laiweifeng.launcher.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;


import com.laiweifeng.launcher.R;
import com.laiweifeng.launcher.base.BaseRecyclerAdapter;
import com.laiweifeng.launcher.base.BaseViewHolder;
import com.laiweifeng.launcher.bean.AppInfo;
import com.laiweifeng.launcher.widget.AppImageView;

import java.util.List;

public class LocalAppAdapter extends BaseRecyclerAdapter<AppInfo> {

    private final PackageManager packageManager;

    public static final int DEFAULT=-1;
    public static final int TOPPING=0;
    public static final int DELETE=1;

    int currentStatus=DEFAULT;
    private OnItemKeyClickListener onItemKeyClickListener;
    private OnItemFocusChangeListener onItemFocusClickListener;

    /**
     * Construction method
     *
     * @param mContext
     * @param mList
     * @param itemLayoutId
     */
    public LocalAppAdapter(Context mContext, List mList, int itemLayoutId) {
        super(mContext, mList, itemLayoutId);
        packageManager = mContext.getPackageManager();
    }


    public void setStatus(int status){
        this.currentStatus=status;
        notifyDataSetChanged();
    }

    public int getStatus(){
        return  this.currentStatus;
    }



    @Override
    public void onBind(BaseViewHolder holder, AppInfo packageInfo, int position) {

        AppImageView appImageView=holder.getView(R.id.appImageview);

        TextView tv_appName=holder.getView(R.id.tv_appName);

        tv_appName.setText(packageInfo.getLabel());
        appImageView.setAppIcon(packageInfo.getIconPath());
        appImageView.setStatus(currentStatus);
        appImageView.setPosition(position);

        appImageView.setOnAppClickListener(new AppImageView.OnAppClickListener() {
            @Override
            public void onAppClick(int position) {
                if(onItemKeyClickListener!=null){
                    onItemKeyClickListener.onItemClick(position);
                }
            }
        });

        appImageView.setOnAppFocusChangeListener(new AppImageView.OnAppFocusChangeListener() {
            @Override
            public void onFocusChange(int position) {
                if(onItemFocusClickListener!=null){
                    onItemFocusClickListener.onItemFocusChange(position);
                }
            }
        });
    }

    public void setOnItemKeyClickListener(OnItemKeyClickListener listener){
        this.onItemKeyClickListener=listener;
    }
    public interface OnItemKeyClickListener{
        void onItemClick(int position);
    }

    public void setOnItemFocusClickListener(OnItemFocusChangeListener listener){
        this.onItemFocusClickListener=listener;
    }
    public interface OnItemFocusChangeListener {
        void onItemFocusChange(int position);
    }
}
