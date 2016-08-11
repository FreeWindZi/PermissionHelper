package com.navy.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.navy.permission.callback.PermissionCallback;
import com.navy.permission.util.PermissionUtil;


public class PermissionHelperTest {














    private Object container;//可以是Activity,也可以是Fragment
    private String[] permissions;
    private PermissionCallback baseCallback;
    private int requestCode;



    private PermissionHelperTest(Object container, String[] permissions, PermissionCallback baseCallback, int requestCode) {
        this.container = container;
        this.permissions = permissions;
        this.baseCallback = baseCallback;
        this.requestCode = requestCode;


    }

    public void requestPermissions() {

        for (String permission: permissions) {
            if (! PermissionUtil.permissionExists(getContext(container), permission)) {
                throw new IllegalArgumentException(permission + " must be define on the AndroidManifest.xml");
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            permissions = PermissionUtil.getDeniedPermissons(getContext(container), permissions);
            if (permissions==null || permissions.length==0){
               baseCallback.onPermissionGranted();
            } else {
              requestPermissions(container, permissions, requestCode);
            }


        } else {
            if (PermissionUtil.checkSelfPermissions(getContext(container), permissions)){
                baseCallback.onPermissionGranted();
            } else {
                baseCallback.onPermissionReject();
            }
        }


    }


    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode != requestCode){
            return;
        }
        if (PermissionUtil.verifyPermissions(grantResults)){
            baseCallback.onPermissionGranted();
        } else {
            baseCallback.onPermissionReject();
        }
    }




    private Context getContext(Object container){
        if (container instanceof Activity){
            return (Activity) container;
        } else if (container instanceof Fragment) {
            return ((Fragment) container).getContext();
        }
//        } else if (container instanceof android.app.Fragment) {
//            return ((android.app.Fragment) container).getContext();
//        }
        throw new IllegalArgumentException("container must be Activity ,or Fragment");
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private void requestPermissions(Object container, @NonNull String[] permissions,int requestCode){
        if (container instanceof Activity){
            ActivityCompat.requestPermissions(((Activity) container), permissions, requestCode);
        } else if (container instanceof Fragment) {
            ((Fragment) container).requestPermissions(permissions, requestCode);
        } else if (container instanceof android.app.Fragment) {
            ((android.app.Fragment) container).requestPermissions(permissions, requestCode);
        } else {
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }




//    public static class WrapperModel {
//        private Object container;//可以是Activity,也可以是Fragment
//        private String[] permissions;
//        private PermissionCallback baseCallback;
//        private int requestCode = 1000;
//
//
//
//        public WrapperModel(Activity activity) {
//            this((Object)activity);
//        }
//
//
//        public WrapperModel(Fragment fragment) {
//            this((Object)fragment);
//        }
//
//
//
//
//        private WrapperModel(android.app.Fragment fragment){
//            this((Object)fragment);
//        }
//
//        private WrapperModel(Object container){
//            this.container = container;
//        }
//
//
//
//
//        public WrapperModel setPermissionsArray(String[] permissions) {
//            return setPermissions(permissions);
//        }
//
//
//
//        public WrapperModel setPermissions(String... permissions) {
//            if (permissions == null || permissions.length == 0) {
//                throw new IllegalArgumentException("permissions is  illegal");
//            }
//            this.permissions = permissions;
//            return this;
//        }
//
//        public WrapperModel setRequestCode(int requestCode) {
//            this.requestCode = requestCode;
//            return this;
//        }
//
//
//        public WrapperModel setPermissionCallback(PermissionCallback baseCallback) {
//            if (baseCallback == null) {
//                throw new IllegalArgumentException("baseCallback is illegal");
//            }
//            this.baseCallback = baseCallback;
//            return this;
//        }
//
//
//        public PermissionHelperTest build() { // 构建，返回一个新对象
//
//            return new PermissionHelperTest(container, permissions, baseCallback, requestCode);
//
//        }
//
//
//    }

}
