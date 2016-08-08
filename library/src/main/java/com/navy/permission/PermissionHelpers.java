package com.navy.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.navy.permission.callback.BaseCallback;
import com.navy.permission.util.LogUtil;
import com.navy.permission.util.PermissionUtil;

import java.util.Set;

/**
 * Created by Navy on 2016/8/3.
 */
public class PermissionHelpers {

    public static String TAG = "===========PermissionHelper";

    public static boolean isDebug = true;
    Context appContext;
    Set<WrapperModel> permissionSet;

    com.navy.permission.PermissionHelpers.WrapperModel model;

    public static PermissionHelpers permissionHelpers;


    public PermissionHelpers getInstance(){
        return this;
    }

    public static void setIsDebug(boolean isDebug) {
        PermissionHelpers.isDebug = isDebug;
    }

    public WrapperModel setActivity(Activity activity){
        if (model != null) {
            model = new WrapperModel();
        }
        model.setPermissions(permissions);
    }


    public WrapperModel setPermissionsArray(String... permissions) {
        if (model != null) {
            model = new WrapperModel();
        }
        model.setPermissions(permissions);
        return model;
    }

    public static class WrapperModel {
        private Object container;//可以是Activity,也可以是Fragment
        private Set<String> permissions;
        private BaseCallback baseCallback;
        private int requestCode = 1000;



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

        /**
         * 这个方法等价于  setPermissions
         *
         * @param permissions
         * @return
         */
        public WrapperModel setPermissionsArray(String[] permissions) {
            return setPermissions(permissions);
        }

        /**
         * 这个方法等价于  setPermissionsArray
         *
         * @param permissions
         * @return
         */
        public WrapperModel setPermissions(String... permissions) {
            if (permissions == null || permissions.length == 0) {
                throw new IllegalArgumentException("permissions is  illegal");
            }

            return this;
        }

        public WrapperModel setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }


        public WrapperModel setBaseCallback(BaseCallback baseCallback) {
            if (baseCallback == null) {
                throw new IllegalArgumentException("baseCallback is illegal");
            }
            this.baseCallback = baseCallback;
            return this;
        }




    }














    /**
     * 使用Builder模式
     */
    public static class WrapperModel {
        private Object container;//可以是Activity,也可以是Fragment
        private String[] permissions;
        private BaseCallback baseCallback;
        private int requestCode = 1000;



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
            checkContext(this.container);
            if (isDebug){
                LogUtil.d("container is ok");
            } else {
                LogUtil.e("container must be Activity ,or Fragment");
            }
        }

        /**
         * 这个方法等价于  setPermissions
         *
         * @param permissions
         * @return
         */
        public WrapperModel setPermissionsArray(String[] permissions) {
            return setPermissions(permissions);
        }

        /**
         * 这个方法等价于  setPermissionsArray
         *
         * @param permissions
         * @return
         */
        public WrapperModel setPermissions(String... permissions) {
            if (permissions == null || permissions.length == 0) {
                throw new IllegalArgumentException("permissions is  illegal");
            }
            this.permissions = permissions;
            return this;
        }

        public WrapperModel setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            if (permissions == null || permissions.length == 0) {
                throw new IllegalArgumentException("permissions is  illegal");
            }
            return this;
        }


        public WrapperModel setBaseCallback(BaseCallback baseCallback) {
            if (baseCallback == null) {
                throw new IllegalArgumentException("baseCallback is illegal");
            }
            this.baseCallback = baseCallback;
            return this;
        }


        public PermissionHelpers build() { // 构建，返回一个新对象


        }


        @TargetApi(value = Build.VERSION_CODES.HONEYCOMB)
        private boolean checkContext(Object container){
            if (container instanceof Activity){
                return true;
            } else if (container instanceof Fragment) {
                return true;
            } else if (container instanceof android.app.Fragment) {
                return true;
            }
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }



}
