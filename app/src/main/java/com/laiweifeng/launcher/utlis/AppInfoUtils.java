package com.laiweifeng.launcher.utlis;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.laiweifeng.launcher.bean.AppInfo;


public class AppInfoUtils {
	


    public static void getApkList(final Context context, final OnCallBack callBack){
        new AsyncTask<Void, Void, ArrayList<AppInfo>>() {
            @Override
            protected ArrayList<AppInfo> doInBackground(Void... voids) {
                ArrayList<AppInfo> saveAppInfo = (ArrayList<AppInfo>) SharePreferenceManager.getObject(context, "APP_LIST");
                ArrayList<String> tempSavePackageNames =new ArrayList<>();
                ArrayList<String> tempPackageNames =new ArrayList<>();
                if(saveAppInfo!=null){
                    for (AppInfo appInfo : saveAppInfo) {
                        tempSavePackageNames.add(appInfo.getPackName());
                    }
                }

                List<PackageInfo> allPackageInfos = context.getPackageManager().getInstalledPackages(0);
                PackageManager pm = context.getPackageManager();
                ArrayList<AppInfo> applicationInfos = new ArrayList<AppInfo>();
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> mApps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
                boolean isok;

                AppInfo appData;

                for (int i = 0; i < allPackageInfos.size(); i++) {
                    appData=new AppInfo();
                    PackageInfo temp = allPackageInfos.get(i);
                    ApplicationInfo appInfo = temp.applicationInfo;
                    if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                            || (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        isok = false;
                        for (ResolveInfo resolveInfo : mApps) {
                            if ((resolveInfo.loadLabel(pm).toString()).equals(pm
                                    .getApplicationLabel(temp.applicationInfo).toString())) {
                                if (!isShuldFiled(temp.packageName )) {
                                    // The system software
                                    isok = true;
                                }

                            }
                        }
                        if (isok) {
                            Bitmap bitmap = ((BitmapDrawable) pm.getApplicationIcon(temp.applicationInfo)).getBitmap();
                            String bitmapSavePath = BitmapUtils.saveBitmap(context, bitmap);
                            appData.setIconPath(bitmapSavePath);
                            appData.setPackName(temp.packageName);
                            appData.setLabel(pm.getApplicationLabel(temp.applicationInfo).toString());
                            appData.setSysttemApk(true);
                            tempPackageNames.add(temp.packageName);
                            if(saveAppInfo!=null){
                                if(!tempSavePackageNames.contains(appData.getPackName())){
                                    saveAppInfo.add(appData);
                                    tempSavePackageNames.add(appData.getPackName());
                                }
                            }else{
                                applicationInfos.add(appData);
                            }
                        }
                    } else {
                        //过滤掉自己
                        if(temp.packageName.equals(context.getPackageName())){
                            continue ;
                        }
                        // The user has installed the software
                        Bitmap bitmap = ((BitmapDrawable) pm.getApplicationIcon(temp.applicationInfo)).getBitmap();
                        String bitmapSavePath = BitmapUtils.saveBitmap(context, bitmap);
                        appData.setIconPath(bitmapSavePath);
                        appData.setPackName(temp.packageName);
                        appData.setLabel(pm.getApplicationLabel(temp.applicationInfo).toString());
                        appData.setSysttemApk(false);
                        tempPackageNames.add(temp.packageName);
                        if(saveAppInfo!=null){
                            if(!tempSavePackageNames.contains(appData.getPackName())){
                                saveAppInfo.add(appData);
                                tempSavePackageNames.add(appData.getPackName());
                            }
                        }else{
                            applicationInfos.add(appData);
                        }
                    }
                }
                if(saveAppInfo!=null){
                    for (int i = 0; i < tempSavePackageNames.size(); i++) {
                        if(!tempPackageNames.contains(tempSavePackageNames.get(i))){
                            AppInfo remove = saveAppInfo.remove(i);
                            Log.v("laiweifeng","remove: "+remove.getPackName());
                        }
                    }
                    return saveAppInfo;
                }else{
                    return applicationInfos;

                }

            }

            @Override
            protected void onPostExecute(ArrayList<AppInfo> packageInfos) {
                super.onPostExecute(packageInfos);
                if(callBack!=null){
                    callBack.success(packageInfos);
                }

            }
        }.execute();

    }




    public static boolean isSystemApp(PackageInfo packageInfo){
        ApplicationInfo appInfo = packageInfo.applicationInfo;
        if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                || (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            return true;
        }
        return  false;

    }

    //过滤掉指定的系统应用
    public static boolean isShuldFiled(String pkgename) {
        //包名
        if (pkgename.equals("your system pagename")) {
            return true;
        }
        return false;
    }

    public interface OnCallBack{
        void success(ArrayList<AppInfo> result);
    }
}
