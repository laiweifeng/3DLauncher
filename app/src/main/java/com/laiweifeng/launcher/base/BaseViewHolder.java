package com.laiweifeng.launcher.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    /**
     * itemView
     */
    public View itemView;
    /**
     * SparseArray save views
     */
    SparseArray<View> views;

    /**
     * construction method
     * @param itemView
     * @param viewType
     */
    public BaseViewHolder(View itemView, int viewType) {
        super(itemView);
        this.itemView = itemView;
        if (viewType==BaseRecyclerAdapter.TYPE_EMPTY||viewType==BaseRecyclerAdapter.TYPE_HEADER||viewType==BaseRecyclerAdapter.TYPE_FOOTER){
            return;
        }
        views = new SparseArray<>();
    }

    /**
     *
     * @param itemview
     * @param viewType
     * @param <T>
     * @return
     */
    public static <T extends BaseViewHolder> T getHolder(View itemview, int viewType){

        return (T) new BaseViewHolder(itemview,viewType);
    }

    /**
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View childreView = views.get(viewId);
        if (childreView == null){
            childreView = itemView.findViewById(viewId);
            views.put(viewId,childreView);
        }
        return (T) childreView;
    }

    /**
     *
     * @param viewId
     * @param listener
     * @return
     */
    public BaseViewHolder setOnclickListener(int viewId,View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}

