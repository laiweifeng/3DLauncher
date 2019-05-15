package com.laiweifeng.launcher.widget;


import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class MyViewPager extends ViewPager {
	

	private static final int TIME_OUT = 0;

	private OnViewPagerKeyListener listener;
	
	private boolean flag=true;


	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPager(Context context) {
		super(context);
	}
	//降低遥控器的左右滑动速度
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_DOWN){
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if(!flag){
					return true;
				}
				flag=false;
				listener.onKey(KeyEvent.KEYCODE_DPAD_LEFT);
				handler.sendEmptyMessageDelayed(TIME_OUT, 500);
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if(!flag){
					return true;
				}
				flag=false;
				listener.onKey(KeyEvent.KEYCODE_DPAD_RIGHT);
				handler.sendEmptyMessageDelayed(TIME_OUT, 500);
				break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				listener.onKey(KeyEvent.KEYCODE_DPAD_CENTER);
				break;

			default:
				break;
			}
		}
		return super.dispatchKeyEvent(event);
	}
	
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			flag=true;
			
		};
	};
	
	
	public void setOnViewPagerKeyListener(OnViewPagerKeyListener listener){
		this.listener=listener;
	}
	
	
	public interface OnViewPagerKeyListener{
		void onKey(int keycode);
	}
	

}
