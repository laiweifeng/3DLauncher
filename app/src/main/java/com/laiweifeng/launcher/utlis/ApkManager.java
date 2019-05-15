package com.laiweifeng.launcher.utlis;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.util.Log;

/**
 * 应用管理类
 *
 * @author Lion
 *
 */
public class ApkManager {

    private static final String TAG = "ApkManager";
    private static final String INSTALL_CMD = "install";
    private static final String UNINSTALL_CMD = "uninstall";

    /**
     * APK静默安装
     *
     * @param apkPath
     *            APK安装包路径
     * @return true 静默安装成功 false 静默安装失败
     */
    public static boolean install(String apkPath) {
        String[] args = { "pm", INSTALL_CMD, "-r", apkPath };
        String result = apkProcess(args);
        Log.e(TAG, "install log:"+result);
        if (result != null
                && (result.endsWith("Success") || result.endsWith("Success\n"))) {
            return true;
        }
        return false;
    }

    /**
     * APK静默安装  需要系统签名
     *
     * @param packageName
     *            需要卸载应用的包名
     * @return true 静默卸载成功 false 静默卸载失败
     */
    public static boolean uninstall(String packageName) {
        String[] args = { "pm", UNINSTALL_CMD, packageName };
        String result = apkProcess(args);
        Log.e(TAG, "uninstall log:"+result);
        if (result != null
                && (result.endsWith("Success") || result.endsWith("Success\n"))) {
            return true;
        }
        return false;
    }

    /**
     * 应用安装、卸载处理
     *
     * @param args
     *            安装、卸载参数
     * @return Apk安装、卸载结果
     */
    public static String apkProcess(String[] args) {
        String result = null;
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }
}