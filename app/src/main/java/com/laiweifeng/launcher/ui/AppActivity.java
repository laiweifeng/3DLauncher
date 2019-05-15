package com.laiweifeng.launcher.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;


import com.laiweifeng.launcher.R;
import com.laiweifeng.launcher.adapter.LocalAppAdapter;
import com.laiweifeng.launcher.bean.AppInfo;
import com.laiweifeng.launcher.dialog.MenuDialog;
import com.laiweifeng.launcher.dialog.UninstallDialog;
import com.laiweifeng.launcher.ui.BaseActivity;
import com.laiweifeng.launcher.utlis.ApkManager;
import com.laiweifeng.launcher.utlis.AppInfoUtils;
import com.laiweifeng.launcher.utlis.SharePreferenceManager;

import java.util.ArrayList;

public class AppActivity extends BaseActivity implements  LocalAppAdapter.OnItemKeyClickListener, LocalAppAdapter.OnItemFocusChangeListener {


    public static final String APK_LIST ="APP_LIST" ;
    private VerticalGridView gridViewVg;
    private LocalAppAdapter localAppAdapter;
    private MenuDialog menuDialog;
    private ArrayList<AppInfo> appList=new ArrayList<>();
    private UninstallDialog uninstallDialog;
    private TextView tv_row;

    BroadcastReceiver installedReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            updateData();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_local_app;
    }

    @Override
    public void init() {

        addLoadView(R.id.rootview);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(installedReceiver, filter);


    }

    @Override
    public void findViews() {

    }

    @Override
    public void initData() {
        this.gridViewVg = ((VerticalGridView)findViewById(R.id.vg_grid_view));
        gridViewVg.setVerticalSpacing(30);
        this.tv_row = findViewById(R.id.tv_row);
        this.gridViewVg.setNumColumns(6);
        localAppAdapter = new LocalAppAdapter(getContext(),appList,R.layout.item_local_app);
        gridViewVg.setAdapter(localAppAdapter);

        showLoading();
         AppInfoUtils.getApkList(getContext(), new AppInfoUtils.OnCallBack() {
            @Override
            public void success(ArrayList<AppInfo> results) {
                appList =results;
                localAppAdapter.refreshData(appList);
                hideLoading();
            }
        });

    }

    @Override
    public void setListener() {
        localAppAdapter.setOnItemKeyClickListener(this);
        localAppAdapter.setOnItemFocusClickListener(this);
    }



    private void updateData() {
        showLoading();
        AppInfoUtils.getApkList(getContext(), new AppInfoUtils.OnCallBack() {
            @Override
            public void success(ArrayList<AppInfo> result) {
                appList = result;
                localAppAdapter.refreshData(appList);
                hideLoading();
                SharePreferenceManager.putObject(getContext(), APK_LIST, appList);

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_MENU:
                menuDialog = new MenuDialog(getContext());
                menuDialog.show();
                menuDialog.setOnMenuItemClickListener(new MenuDialog.OnMenuItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case MenuDialog.TOPPING:
                                localAppAdapter.setStatus(LocalAppAdapter.TOPPING);
                                menuDialog.dismiss();
                                break;

                            case MenuDialog.DELETE_APP:
                                localAppAdapter.setStatus(LocalAppAdapter.DELETE);
                                menuDialog.dismiss();
                                break;
                        }
                    }
                });
                break;

            case KeyEvent.KEYCODE_BACK:
                int status = localAppAdapter.getStatus();
                if(status!=LocalAppAdapter.DEFAULT){
                    localAppAdapter.setStatus(LocalAppAdapter.DEFAULT);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemFocusChange(final int position) {
    }

    public void showLoading(){
        getBackgroundLoadView().showLoading();
    }
    public void hideLoading(){
        getBackgroundLoadView().hideLoadStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(installedReceiver);
    }

    @Override
    public void onItemClick(final int position) {
        int status = localAppAdapter.getStatus();
        switch (status){
            case LocalAppAdapter.TOPPING:
                AppInfo remove = appList.remove(position);
                appList.add(0,remove);
                localAppAdapter.refreshData(appList);

                SharePreferenceManager.putObject(getContext(),APK_LIST,appList);
                break;

            case LocalAppAdapter.DELETE://静默卸载应用需要系统签名
                if(appList.get(position).isSysttemApk()){
                    Toast.makeText(getContext(), R.string.unable_uninstall,Toast.LENGTH_LONG).show();
                    return;
                }
                uninstallDialog =new UninstallDialog(getContext());
                uninstallDialog.show();
                uninstallDialog.setOnOkClickListener(new UninstallDialog.OnOkClickListener() {
                    @Override
                    public void onClick() {

                        uninstallDialog.showLoading();
                        String packageName = appList.get(position).getPackName();
                        boolean result = ApkManager.uninstall(packageName);
                        if(result){//卸载成功
                            updateData();
                        }
                        uninstallDialog.dismiss();

                    }
                });
                break;

            default:
                Intent intent = getPackageManager().getLaunchIntentForPackage( appList.get(position).getPackName());
                if (intent != null) {
                    startActivity(intent);
                }
                break;
        }
    }
}
