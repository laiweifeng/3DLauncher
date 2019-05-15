package com.laiweifeng.launcher.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.R.string;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.laiweifeng.launcher.utlis.AnimUtils;

public class ItemPagerAdapter extends PagerAdapter{
	
	public static final int LEFT_DIRECTION=0; 
	public static final int RIGHT_DIRECTION=1; 
	public static boolean flag; 
	public int index;
	
	private ArrayList<Integer> drawables;
	private int direction=-1;


	public void update(){
		drawables.add(drawables.remove(0));
		notifyDataSetChanged();
	}
	
	public ItemPagerAdapter(ArrayList<Integer> drawables) {
		this.drawables=drawables;
	}
	
	public void setDirection(int direction){
		this.direction =direction ;
	} 

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
    	int newPosition=position%drawables.size();
    	ImageView view = new ImageView(container.getContext());
    	view.setTag(newPosition);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setAdjustViewBounds(true);
        view.setImageResource(drawables.get(newPosition));
        view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        AnimIn(view);
        container.addView(view);
        return view;
    }
    
  

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
    public void destroyItem(final ViewGroup container, int position, Object object) {
    	final View view=(View) object;
    	view.setElevation(0);
    	switch (direction) {
		case LEFT_DIRECTION:

			AnimUtils.rightOut(view, new AnimUtils.OnAnimListener() {
				@Override
				public void onAnimEnd() {
					container.removeView(view);
				}
			});
			break;
		case RIGHT_DIRECTION:
			AnimUtils.leftOut(view, new AnimUtils.OnAnimListener() {
				@Override
				public void onAnimEnd() {
					// TODO Auto-generated method stub
					container.removeView(view);
				}
			});
			break;
			
		default:
			container.removeView(view);
			break;
		}
    }

    @Override
    public int getCount() {
//        return drawables.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
    
    
    private void AnimIn(View view) {
		switch (direction) {
		case LEFT_DIRECTION:
			AnimUtils.leftIn(view, new AnimUtils.OnAnimListener() {
				@Override
				public void onAnimEnd() {
					
				}
			});
			break;
		case RIGHT_DIRECTION:
			AnimUtils.rightIn(view, new AnimUtils.OnAnimListener() {
				@Override
				public void onAnimEnd() {
					
				}
			});
			break;
		}
	}

}
