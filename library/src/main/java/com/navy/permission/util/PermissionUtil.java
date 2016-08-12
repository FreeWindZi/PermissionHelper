package com.navy.permission.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by Navy on 2016/8/6.
 */
public class PermissionUtil {


    public static boolean verifyPermissions(int[] grantResults) {
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static boolean checkSelfPermissions(Context context, String[] permissionName){
        for (String permission : permissionName) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     *判断 权限是否在AndroidManifest.xml声请
     */
    public static  boolean permissionExists(Context context, @NonNull String permissionName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 得到所有没有获得权限
     */
    public static String[] getDeniedPermissons(Context context, String[] permissionName){
        ArrayList<String> needPermissons = new ArrayList<>();
        for (String permission: permissionName){
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermissons.add(permission);
            }
        }
        return needPermissons.toArray(new String[needPermissons.size()]);
    }
}
