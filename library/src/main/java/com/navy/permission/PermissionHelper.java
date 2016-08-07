package com.navy.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.navy.permission.callback.PermissionCallback;
import com.navy.permission.util.PermissionUtil;

/**
 * Created by Navy on 2016/8/3.
 */
public class PermissionHelper{

    private Activity activity;
    private String[] permissions;
    private PermissionCallback permissionCallback;
    private int requestCode;

    private Fragment fragment;

    private Context context;


    private PermissionHelper(Activity activity,  Fragment fragment, String[] permissions, PermissionCallback permissionCallback, int requestCode) {
        this.activity = activity;
        this.fragment = fragment;
        this.permissions = permissions;
        this.permissionCallback = permissionCallback;
        this.requestCode = requestCode;

        if (activity != null){
            context = activity;
        }
        if (fragment != null){
            context = fragment.getContext();
        }
    }

    public void requestPermissions() {

        for (String permission: permissions) {
            if (! PermissionUtil.permissionExists(context, permission)) {
                throw new IllegalArgumentException(permission + " must be define on the AndroidManifest.xml");
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            permissions = PermissionUtil.getDeniedPermissons(context, permissions);
            if (permissions==null || permissions.length==0){
               permissionCallback.onPermissionGranted();
            } else {
                if (activity == null) {
                    fragment.requestPermissions(permissions, requestCode);
                } else {
                    activity.requestPermissions(permissions, requestCode);
                }
            }


        } else {
            if (PermissionUtil.checkSelfPermissions(context, permissions)){
                permissionCallback.onPermissionGranted();
            } else {
                permissionCallback.onPermissionReject();
            }
        }


    }


    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode != requestCode){
            return;
        }
        if (PermissionUtil.verifyPermissions(grantResults)){
            permissionCallback.onPermissionGranted();
        } else {
            permissionCallback.onPermissionReject();
        }
    }




    /**
     * 使用Builder模式
     */
    public static class Builder {
        private Activity activity;

        private Fragment fragment;
        private String[] permissions;
        private PermissionCallback permissionCallback;
        private int requestCode = 1000;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         在Fragment中申请权限，不要使用ActivityCompat.requestPermissions,
         直接使用Fragment的requestPermissions方法，否则会回调到Activity的onRequestPermissionsResult
         * @param fragment
         */
        public Builder(Fragment fragment) {
            this.fragment = fragment;
        }

        /**
         * 这个方法等价于  setPermissions
         *
         * @param permissions
         * @return
         */
        public Builder setPermissionsArray(String[] permissions) {
            return setPermissions(permissions);
        }

        /**
         * 这个方法等价于  setPermissionsArray
         *
         * @param permissions
         * @return
         */
        public Builder setPermissions(String... permissions) {
            if (permissions == null || permissions.length == 0) {
                throw new IllegalArgumentException("permissions is  illegal");
            }
            this.permissions = permissions;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }


        public Builder setPermissionCallback(com.navy.permission.callback.PermissionCallback permissionCallback) {
            if (permissionCallback == null) {
                throw new IllegalArgumentException("permissionCallback is illegal");
            }
            this.permissionCallback = permissionCallback;
            return this;
        }


        public PermissionHelper build() { // 构建，返回一个新对象

            return new PermissionHelper(activity, fragment,permissions, permissionCallback, requestCode);

        }


    }

}
