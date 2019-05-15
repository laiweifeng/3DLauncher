package com.laiweifeng.launcher.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener, View.OnKeyListener {
    /**
     * Context
     */
    private Context mContext;
    /**
     * data mList
     */
    private List<T> mList;
    /**
     * LayoutInflater
     */
    private LayoutInflater inflater;
    /**
     * TYPE_HEADER
     */
    public static final int TYPE_HEADER = 0;
    /**
     * TYPE_NORMAL
     */
    public static final int TYPE_NORMAL = 1;
    /**
     * TYPE_EMPTY
     */
    public static final int TYPE_EMPTY = 2;
    /**
     * TYPE_FOOTER
     */
    public static final int TYPE_FOOTER = 3;
    /**
     * emptyView
     */
    private View mEmptyView;
    /**
     * headerView
     */
    private View mHeaderView;
    /**
     * footerView
     */
    private View mFooterView;
    /**
     * itemLayoutId
     */
    private int itemLayoutId;
    private OnSlideToBottomListener onSlideToBottomListener;

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.v("laiweifeng","onClick  000");
        if (listener!=null){
            Integer pos = (Integer) v.getTag();
            if (pos!=null&&pos!=-1){
                listener.onItemClick((Integer) v.getTag(),v);
                return;
            }
            if (mEmptyView!=null&&emptyViewClickListener!=null){
                emptyViewClickListener.onEmptyViewClick();
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * item 点击监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener listener=null;

    /**
     * 为item 添加点击监听
     * @param listener BaseRecyclerAdapter.OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    /**
     * emptyView 点击监听接口
     */
    public interface OnEmptyViewClickListener{
        void onEmptyViewClick();
    }

    /**
     * OnEmptyViewClickListener
     */
    private OnEmptyViewClickListener emptyViewClickListener = null;

    /**
     * 为emptyView 添加点击监听
     * @param listener
     */
    public void setOnEmptyViewClickListener(OnEmptyViewClickListener listener){
        this.emptyViewClickListener = listener;
    }

    public void setOnSlideToBottomListener(OnSlideToBottomListener listener){
        this.onSlideToBottomListener=listener;
    }

    public interface OnSlideToBottomListener{
        void onBottom();
    }

    /**
     * Construction method
     * @param mContext
     * @param mList
     * @param itemLayoutId
     */
    public BaseRecyclerAdapter(Context mContext, List<T> mList, int itemLayoutId) {
        this.mContext = mContext;
        this.mList = mList;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(mContext);
    }

    public Context getContext(){
        return mContext;
    }

    /**
     *
     * @return mList
     */
    public List<T> getmList() {
        return mList;
    }

    /**
     * @param mList
     */
    public void setmList(List<T> mList) {
        this.mList = mList;
    }
    public void addmList(List<T> mList) {
        this.mList.addAll(mList) ;
    }

    /**
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * @param footerView
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * @param view
     */
    public void setEmptyView(View view) {
        mEmptyView = view;
        notifyDataSetChanged();
    }

    /**
     * @return mHeaderView
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return BaseViewHolder
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType){
            case TYPE_EMPTY:
                itemView = mEmptyView;
                break;
            case TYPE_HEADER:
                itemView = mHeaderView;
                break;
            case TYPE_FOOTER:
                itemView = mFooterView;
                break;
            case TYPE_NORMAL:
                itemView = LayoutInflater.from(mContext).inflate(itemLayoutId,parent,false);
                break;
            default:
                itemView = mEmptyView;
                break;
        }
        itemView.setOnClickListener(this);
        itemView.setOnKeyListener(this);
        return BaseViewHolder.getHolder(itemView,viewType);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_FOOTER&&mList.size()>9){
            if(onSlideToBottomListener!=null){
                onSlideToBottomListener.onBottom();
            }
        }
        if (getItemViewType(position) == TYPE_HEADER||getItemViewType(position) == TYPE_EMPTY || getItemViewType(position) == TYPE_FOOTER){
            holder.itemView.setTag(-1);
            return;
        }
        int pos = getRealPosition(holder);
        holder.itemView.setTag(pos);
        onBind(holder,mList.get(pos),pos);
    }

    /**
     * @param holder
     * @param t
     * @param position
     */
    public abstract void onBind(BaseViewHolder holder,T t,int position);

    /**
     *
     * @param position
     * @return ViewType
     */
    @Override
    public int getItemViewType(int position) {//判断顺序固定
        if (position == 0 && mHeaderView != null) return TYPE_HEADER;
        if (mEmptyView != null && mList.size() == 0) return TYPE_EMPTY;
        if (mHeaderView == null && mFooterView == null) return TYPE_NORMAL;
        if (mFooterView != null && position == getItemCount() - 1) return TYPE_FOOTER;
        return TYPE_NORMAL;
    }

    /**
     * @return itemCount
     */
    @Override
    public int getItemCount() {
        int itemCount = mList.size();
        if (null != mEmptyView && itemCount == 0) {
            itemCount++;
        }
        if (null != mHeaderView) {
            itemCount++;
        }
        if (null != mFooterView) {
            itemCount++;
        }
        return itemCount;
    }

    /**
     * @param holder
     * @return position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public void refreshData(List<T> list){
        setmList(list);
        notifyDataSetChanged();
    }
    public void addData(List<T> list){
        addmList(list);
        notifyDataSetChanged();
    }

}
