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
import com.navy.permission.util.PermissionUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Navy on 2016/8/3.
 */
public class PermissionHelper {

    public static String TAG = "===========PermissionHelperTest";

    private   boolean isDebug = true;
    Context appContext;
    Set<WrapperModel> permissionSet;

    PermissionHelper.WrapperModel model = null;

    public static PermissionHelper permissionHelper = null;


    public static PermissionHelper getInstance(){
        if (permissionHelper == null){
            permissionHelper = new PermissionHelper();
        }
        return permissionHelper;
    }

    private PermissionHelper(){
        
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public PermissionHelper with(Activity activity){
        model = new WrapperModel(activity);
        return this;
    }

    public PermissionHelper with(android.app.Fragment fragment){
        model = new WrapperModel(fragment);
        return this;
    }

    public PermissionHelper with(Fragment fragment){
        model = new WrapperModel(fragment);
        return this;
    }


    public PermissionHelper setPermissions(String... permissions){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setPermissions(permissions);
        return this;
    }

    public PermissionHelper setRequestCode(int requestCode){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setRequestCode(requestCode);
        return this;
    }



    public PermissionHelper setPermissonCallback(PermissonCallback callback){
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.setPermissonCallback(callback);
        return this;
    }

    public void requestPermissions(){
        LogUtil.d("开始检查输入的参数");
        checkWrapperModel(model);

        LogUtil.d("输入参数合法-------" + model.toString());
        if (permissionSet == null) {
            permissionSet = new HashSet<>();
        }
        if ( permissionSet.contains(model)){
            LogUtil.e("you must hava different requestCode or permissions");
            model = null;
            return;
        }

        ///

        permissionSet.add(model);
        LogUtil.d("permissionSet的长度加1   +++" + "permissionSet的长度为:" + permissionSet.size());
        model = null;
        LogUtil.d("设置model为null  ");
        Iterator<WrapperModel> iter = permissionSet.iterator();
        while (iter.hasNext()) {
            WrapperModel tempModel = iter.next();
            boolean isNeedResult=requestPermissionsModel(tempModel);
            if (!isNeedResult){
                LogUtil.d("permissionSet的长度减1 ---- " );
                iter.remove();
            }
        }
        LogUtil.d("权限声请完毕   permissionSet的长度为:" +  permissionSet.size());
    }


    //返回的参数 表示是否要回调onRequestPermissionsResult  如果需要回调permissionSet 就不删除
    private boolean requestPermissionsModel(WrapperModel pModel){
        if (appContext == null){
            appContext = getContext(pModel.container).getApplicationContext();
        }

        for (String permission: pModel.permissions) {
            if (! PermissionUtil.permissionExists(appContext, permission)) {
                throw new IllegalArgumentException(permission + " must be define on the AndroidManifest.xml");
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            pModel.permissions = PermissionUtil.getDeniedPermissons(appContext, pModel.permissions);
            if (pModel.permissions==null || pModel.permissions.length==0){
                pModel.permissonCallback.onPermissionGranted();
            } else {

                requestPermissions(pModel.container, pModel.permissions, pModel.requestCode);
                return true;

            }


        } else {
            if (PermissionUtil.checkSelfPermissions(appContext, pModel.permissions)){
                pModel.permissonCallback.onPermissionGranted();
            } else {
                pModel.permissonCallback.onPermissionReject();
            }
        }
        return false;
    }



    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        LogUtil.d("请求回调 权限参数回调开始");
        WrapperModel tempModel = new WrapperModel();
        tempModel.setPermissions(permissions);
        tempModel.setRequestCode(requestCode);
        if (!permissionSet.contains(tempModel)) {
            LogUtil.e("请求参赛与请求回调参数不同, 请仔细检查");
            throw new IllegalArgumentException("请求参赛与请求回调参数不同, 请仔细检查");
        }

        Iterator<WrapperModel> iter = permissionSet.iterator();
        while (iter.hasNext()) {
            WrapperModel model = iter.next();
            if (model.equals(tempModel)) {
                if (PermissionUtil.verifyPermissions(grantResults)){
                    model.permissonCallback.onPermissionGranted();
                } else {
                    model.permissonCallback.onPermissionReject();
                }
                break;
            }
        }
        LogUtil.d("权限借口回调完成");
        permissionSet.remove(tempModel);
        LogUtil.d("permissionSet 移除数据 " + "permissionSet的长度为:" + permissionSet.size());



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
        } else {
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private synchronized void requestPermissions(Object container, @NonNull String[] permissions,int requestCode){
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

        private WrapperModel(){
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
//            if (container != null ? !container.equals(that.container) : that.container != null)
//                return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(permissions, that.permissions))
                return false;

            return true;
            //不适用permissonCallback 判断是否相等, 因为在onRequestPermissionsResult PermissonCallback中不能得到该对象

//            return !(permissonCallback != null ? !permissonCallback.equals(that.permissonCallback) : that.permissonCallback != null);

        }

        @Override
        public int hashCode() {
            int result = 0;
            //result = container != null ? container.hashCode() : 0;
            result = 31 * result + (permissions != null ? Arrays.hashCode(permissions) : 0);
            //result = 31 * result + (permissonCallback != null ? permissonCallback.hashCode() : 0);
            result = 31 * result + requestCode;
            return result;
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
