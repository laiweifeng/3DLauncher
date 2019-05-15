package com.laiweifeng.launcher.widget;
import android.support.v4.view.ViewPager;
import android.view.View;

public class GalleryTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
    	//-1左边完全不可见   0 完全可见   右边1完全不可见
    	//从左到右  左： 0- 到-1  -0.1      右 1 到 0  0.xx
    	float scale = 0.2f;
        float scaleValue = 1 - Math.abs(position) * scale;
        view.setScaleX(scaleValue);
        view.setScaleY(scaleValue);
//        view.setAlpha(scaleValue);
        view.setPivotX((view.getWidth()) * (1 - position - (position > 0 ? 1 : -1) * 3f) * 0.5f);
        
//        view.setElevation(position > -0.25 && position < 0.25 ? 1 : 0);
        

    	if(position<-1.25&&position>-2.25){//    -1.25  -1.26   -2    -2.25
    		view.setElevation(1);
    	}else if(position<-0.25&&position>-1.25){//   -0.75     -1    -1.25
    		view.setElevation(2);
    	}else if(position > -0.5 && position < 0.5){//  -0.25  -0.24   0   0.24  0.25
    		view.setElevation(3);
    	}else if(position> 0.75&&position<1.25){//    0.75   0.76    1     1.24  1.25
    		view.setElevation(2);
    	}else if(position>1.75&&position<2.25){ //      1.75  1.76   2   2.24 2.25
    		view.setElevation(1);
    	}else{
    		view.setElevation(0);
    	}
    }
    
    
    
}
