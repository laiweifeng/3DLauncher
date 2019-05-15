package com.laiweifeng.launcher.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.laiweifeng.launcher.widget.LoadStatusView;

public abstract class BaseActivity extends FragmentActivity {
	
	private Context mContext;
	private LoadStatusView backgroundLoadView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext=this;
		setContentView(getLayoutId());
		backgroundLoadView = new LoadStatusView(getContext());
		init();
		findViews();
		initData();
		setListener();
	}

	/**
	 * 添加加载状态背景
	 */
	public void addLoadView(int rootViewId) {
		((ViewGroup)findViewById(rootViewId)).addView(backgroundLoadView);
	}

	public LoadStatusView getBackgroundLoadView(){
		return backgroundLoadView;
	}

	protected abstract int getLayoutId();

	public Context getContext(){
		return mContext;
	}

	public abstract void init();

	public abstract void findViews();
	public abstract void initData();
	public abstract void setListener();
	

}
