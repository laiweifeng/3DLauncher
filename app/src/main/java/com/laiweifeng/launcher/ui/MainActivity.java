package com.laiweifeng.launcher.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.laiweifeng.launcher.R;
import com.laiweifeng.launcher.adapter.ItemPagerAdapter;
import com.laiweifeng.launcher.ui.BaseActivity;
import com.laiweifeng.launcher.utlis.L;
import com.laiweifeng.launcher.widget.GalleryTransformer;
import com.laiweifeng.launcher.widget.MyViewPager;
import com.laiweifeng.launcher.widget.ViewPagerScroller;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private MyViewPager mViewPager;
    private ItemPagerAdapter pagerAdapter;
    private ArrayList<Integer> drawables;
    private TextView mUserName;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

    }


    @Override
    public void findViews() {
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        mUserName = (TextView) findViewById(R.id.tv_userName);
    }


    @Override
    public void initData() {

        mUserName.setText("3D Launcher");

        drawables = new ArrayList<>();
        drawables.add(R.drawable.media);
        drawables.add(R.drawable.settings);
        drawables.add(R.drawable.iptv);
        drawables.add(R.drawable.hdmi);
        drawables.add(R.drawable.apps);

        pagerAdapter = new ItemPagerAdapter(drawables);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2);
        mViewPager.setPageTransformer(true, new GalleryTransformer());
        new ViewPagerScroller(this).setViewPagerScrollSpeed(mViewPager,800);
    }

    @Override
    public void setListener() {
        mViewPager.setOnViewPagerKeyListener(new MyViewPager.OnViewPagerKeyListener() {

            @Override
            public void onKey(int keycode) {
                switch (keycode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        pagerAdapter.setDirection(ItemPagerAdapter.LEFT_DIRECTION);
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        pagerAdapter.setDirection(ItemPagerAdapter.RIGHT_DIRECTION);
                        break;
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                        int currentItem = mViewPager.getCurrentItem()%drawables.size();
                        toPage(currentItem);
                        break;
                }
            }

        });
    }


    @Override
    public void onBackPressed() {}

    private void toPage(int currentItem) {
        Intent intent=new Intent();
        intent.setClass(getContext(),AppActivity.class);
        startActivity(intent);
    }



}
