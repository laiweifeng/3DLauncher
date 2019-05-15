package com.laiweifeng.launcher.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class ViewPagerScroller extends Scroller {  
    private int mDuration;  
  
    public ViewPagerScroller(Context context) {  
        super(context);  
    }  
  
    public ViewPagerScroller(Context context, Interpolator interpolator) {  
        super(context, interpolator);  
    }  
  
    public void setDuration(int mDuration) {
        this.mDuration = mDuration;  
    }  
  
    @Override  
    public void startScroll(int startX, int startY, int dx, int dy) {  
        super.startScroll(startX, startY, dx, dy, this.mDuration);  
    }  
  
    @Override  
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {  
        super.startScroll(startX, startY, dx, dy, this.mDuration);  
    }

    /**
     *   Viewpager 切换速度
     * @param viewPager
     * @param speed  毫秒
     */
    public void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, this);
            setDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
  
}