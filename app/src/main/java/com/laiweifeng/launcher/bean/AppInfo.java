package com.laiweifeng.launcher.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AppInfo implements Serializable {

    private String packName;
    private String label;
//    private Drawable drawableIcon;
    private boolean isSysttemApk;
    private String iconPath;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

//    public Drawable getDrawableIcon() {
//        return drawableIcon;
//    }
//
//    public void setDrawableIcon(Drawable drawableIcon) {
//        this.drawableIcon = drawableIcon;
//    }

    public boolean isSysttemApk() {
        return isSysttemApk;
    }

    public void setSysttemApk(boolean systtemApk) {
        isSysttemApk = systtemApk;
    }
}
