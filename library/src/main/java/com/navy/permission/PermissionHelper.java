package com.navy.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.navy.permission.callback.PermissonCallback;
import com.navy.permission.util.LogUtil;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Navy on 2016/8/3.
 */
public class PermissionHelper {

    public static String TAG = "===========PermissionHelperTest";

    private   boolean isDebug = true;
    Context appContext;
    Set<WrapperModel> permissionSet;

    PermissionHelper.WrapperModel model = null;

    public static PermissionHelper permissionHelper;


    public static PermissionHelper getInstance(){
        return new PermissionHelper();
    }

    private PermissionHelper(){
        
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public WrapperModel with(Activity activity){
        model = new WrapperModel(activity);
        return model;
    }

    public WrapperModel with(android.app.Fragment fragment){
        model = new WrapperModel(fragment);
        return model;
    }

    public WrapperModel with(Fragment fragment){
        model = new WrapperModel(fragment);
        return model;
    }


    public WrapperModel setPermissions(String... permissions){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setPermissions(permissions);
        return model;
    }

    public WrapperModel setRequestCode(int requestCode){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setRequestCode(requestCode);
        return model;
    }



    public WrapperModel setPermissonCallback(PermissonCallback callback){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setPermissonCallback(callback);
        return model;
    }

    public void requestPermission(){
        LogUtil.d("开始检查输入的参数");
        checkWrapperModel(model);
        if (appContext == null){
            appContext = getContext(model.container).getApplicationContext();
        }
        LogUtil.d("输入参数合法-------" +model.toString());
        if (! permissionSet.add(model)){
            throw new IllegalArgumentException("you must hava different requestCode or permissions or container");
        }
        model = null;

        LogUtil.d("设置model为null  "+ "permissionSet的长度为:" +permissionSet.size());
        for (WrapperModel wrapperModel: permissionSet) {

        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

    }













    public static void checkWrapperModel(PermissionHelper.WrapperModel wrapperModel){
        if (wrapperModel == null) {
            throw new NullPointerException("WrapperModel cannot be null");
        }
        if (wrapperModel.container == null){
            throw new NullPointerException("WrapperModel container cannot be null");
        }
        if (!(wrapperModel.container instanceof Activity
                || wrapperModel.container instanceof android.app.Fragment
                || wrapperModel.container instanceof Fragment)){
            throw new IllegalArgumentException("WrapperModel container must be Activity ,or Fragment");
        }

        if (wrapperModel.requestCode <= 0){
            throw new IllegalArgumentException("RequestCode must >0");
        }
        if (wrapperModel.permissions== null || wrapperModel.permissions.length==0){
            throw new IllegalArgumentException("permissions cannot be null, or it's lenth must >0");
        }
        if (wrapperModel.permissonCallback == null) {
            throw new NullPointerException("permissonCallback cannot be null");
        }
    }



    private Context getContext(Object container){
        if (container instanceof Activity){
            return (Activity) container;
        } else if (container instanceof Fragment) {
            return ((Fragment) container).getContext();
        } else if (container instanceof android.app.Fragment) {
            return ((android.app.Fragment) container).getContext();
        }
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






    private static class WrapperModel {
        private Object container;//可以是Activity,也可以是Fragment
        private String[] permissions;
        private PermissonCallback permissonCallback;
        private int requestCode;

        private WrapperModel(){};

        public WrapperModel(Activity activity) {
            this((Object)activity);
        }


        public WrapperModel(Fragment fragment) {
            this((Object)fragment);
        }

        /**
         * android.support.v4.app.Fragment , 不推荐
         * @param fragment
         */
        private WrapperModel(android.app.Fragment fragment){
            this((Object)fragment);
        }

        private WrapperModel(Object container){
            this.container = container;

        }
        public void setPermissions(String... permissions) {
            this.permissions = permissions;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }


        public void setPermissonCallback(PermissonCallback permissonCallback) {
            this.permissonCallback = permissonCallback;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WrapperModel that = (WrapperModel) o;

            if (requestCode != that.requestCode) return false;
            if (container != null ? !container.equals(that.container) : that.container != null)
                return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(permissions, that.permissions))
                return false;

            return true;
            //不适用permissonCallback 判断是否相等, 因为在onRequestPermissionsResult PermissonCallback中不能得到该对象

//            return !(permissonCallback != null ? !permissonCallback.equals(that.permissonCallback) : that.permissonCallback != null);

        }

        @Override
        public String toString() {
            return "WrapperModel{" +
                    "container=" + container +
                    ", permissions=" + Arrays.toString(permissions) +
                    ", permissonCallback=" + permissonCallback +
                    ", requestCode=" + requestCode +
                    '}';
        }
    }



}
