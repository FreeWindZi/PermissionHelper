package com.navy.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.navy.permission.callback.PermissionCallback;
import com.navy.permission.callback.PermissionDetailCallback;
import com.navy.permission.util.LogUtil;
import com.navy.permission.util.PermissionUtil;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Navy on 2016/8/3.
 */
public class PermissionHelper {

    public static String TAG = "===========PermissionHelperTest";

    private boolean isDebug = true;
    private Context appContext;
    private PermissionHelper.WrapperModel model = null;
    public static PermissionHelper permissionHelper = null;

    private int requestPermissionsLenth = 0;  //表示已经接收到的权限的数量  如果接口是PermissionDetailCallback  权限由两部分组成

    public static PermissionHelper getInstance() {
        if (permissionHelper == null) {
            permissionHelper = new PermissionHelper();
        }
        return permissionHelper;
    }

    private PermissionHelper() {

    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public PermissionHelper with(Activity activity) {
        model = new WrapperModel(activity);
        return this;
    }

    public PermissionHelper with(android.app.Fragment fragment) {
        model = new WrapperModel(fragment);
        return this;
    }

    public PermissionHelper with(Fragment fragment) {
        model = new WrapperModel(fragment);
        return this;
    }


    public PermissionHelper setPermissions(String... permissions) {
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.permissions=permissions;
        return this;
    }

    public PermissionHelper setRequestCode(int requestCode) {
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.requestCode =requestCode ;
        return this;
    }

    public PermissionHelper setForceAccepting(boolean forceAccepting) {
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.forceAccepting = forceAccepting;
        return this;
    }

    public PermissionHelper setPermissonCallback(PermissionCallback callback) {
        if (model == null) {
            throw new IllegalArgumentException("you must call wth() mothed first");
        }
        model.permissionCallback = callback;
        return this;
    }

    public void requestPermissions() {
        checkWrapperModel(model);
        requestPermissionsModel(model);

    }


    private void requestPermissionsModel(WrapperModel pModel) {
        if (appContext == null) {
            appContext = getContext(pModel.container).getApplicationContext();
        }

        for (String permission : pModel.permissions) {
            if (!PermissionUtil.permissionExists(appContext, permission)) {
                throw new IllegalArgumentException(permission + " must be define on the AndroidManifest.xml");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            pModel.permissions = PermissionUtil.getDeniedPermissons(appContext, pModel.permissions);
            if (pModel.permissions == null || pModel.permissions.length == 0) {
                pModel.permissionCallback.onPermissionGranted();
                afterPermissonCall();
            } else {
                //如果接口是PermissionDetailCallback 添加回调解释接口
                if (pModel.permissionCallback instanceof PermissionDetailCallback) {
                    //add by navy
                    List<String> noExplainPermissions = new ArrayList<>();
                    List<String> explainPermissions = new ArrayList<>();

                    for (String permission : pModel.permissions) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(pModel.container),
                                permission)) {

                            explainPermissions.add(permission);
                        } else {
                            noExplainPermissions.add(permission);
                        }

                    }
                    if (explainPermissions.size() !=0) {
                        ((PermissionDetailCallback) pModel.permissionCallback).onPermissionExplained(explainPermissions.toArray(new String[]{}));
                    }

                    if (noExplainPermissions.size() != 0) {
                        requestPermissions(model.container, noExplainPermissions.toArray(new String[]{}), model.requestCode);
                    }

                } else {
                    requestPermissions(pModel.container, pModel.permissions, pModel.requestCode);

                }


            }


        } else {
            if (PermissionUtil.checkSelfPermissions(appContext, pModel.permissions)) {
                pModel.permissionCallback.onPermissionGranted();
            } else {
                pModel.permissionCallback.onPermissionReject();
            }
            afterPermissonCall();
        }
    }


    private void afterPermissonCall() {
        model = null;
        appContext = null;
        requestPermissionsLenth = 0;
        LogUtil.d( "权限回调完成");
    }


    /**
     * 只为实现了 PermissionDetailCallback 调用
     *
     * @param permission
     */
    public void requestAfterExplanation(String[] permission) {
        if (permission.length != 0) { //表示每一个都需要解释
            requestPermissions(model.container, permission, model.requestCode);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtil.d("请求权限" + "回调的requestCode=" + requestCode + "  而请求的requestCode = "+model.requestCode);

        if (requestCode == model.requestCode) {
            String []deniedPermissons = PermissionUtil.getDeniedPermissons(appContext, model.permissions);
            if (PermissionUtil.getDeniedPermissons(appContext, model.permissions).length == 0) {
                model.permissionCallback.onPermissionGranted();
                afterPermissonCall();
            } else {
                requestPermissionsLenth += permissions.length;
                if (requestPermissionsLenth == model.permissions.length) {
                    model.permissionCallback.onPermissionReject();
                    if (model.forceAccepting) { // 再次声请权限
                        model.permissions = deniedPermissons;
                        requestPermissionsLenth = 0;
                        requestPermissionsModel(model);
                    } else {
                        afterPermissonCall();
                    }

                }

            }


        } else {
            LogUtil.e("发生未知错误" + "回调的requestCode=" + requestCode + "  而请求的requestCode = model.requestCode");
        }
    }




    public static void checkWrapperModel(PermissionHelper.WrapperModel wrapperModel) {
        if (wrapperModel == null) {
            throw new NullPointerException("WrapperModel cannot be null");
        }
        if (wrapperModel.container == null) {
            throw new NullPointerException("WrapperModel container cannot be null");
        }
        if (!(wrapperModel.container instanceof Activity
                || wrapperModel.container instanceof android.app.Fragment
                || wrapperModel.container instanceof Fragment)) {
            throw new IllegalArgumentException("WrapperModel container must be Activity ,or Fragment");
        }

        if (wrapperModel.requestCode <= 0) {
            throw new IllegalArgumentException("RequestCode must >0");
        }
        if (wrapperModel.permissions == null || wrapperModel.permissions.length == 0) {
            throw new IllegalArgumentException("permissions cannot be null, or it's lenth must >0");
        }
        if (wrapperModel.permissionCallback == null) {
            throw new NullPointerException("permissionCallback cannot be null");
        }
    }


    private Context getContext(Object container) {
        if (container instanceof Activity) {
            return (Activity) container;
        } else if (container instanceof Fragment) {
            return ((Fragment) container).getContext();
        } else if (container instanceof android.app.Fragment) {
            return ((android.app.Fragment) container).getContext();
        } else {
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }

    private Activity getActivity(Object container) {
        if (container instanceof Activity) {
            return (Activity) container;
        } else if (container instanceof Fragment) {
            return ((Fragment) container).getActivity();
        } else if (container instanceof android.app.Fragment) {
            return ((android.app.Fragment) container).getActivity();
        } else {
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private synchronized void requestPermissions(Object container, @NonNull String[] permissions, int requestCode) {
        if (container instanceof Activity) {
            ActivityCompat.requestPermissions(((Activity) container), permissions, requestCode);
        } else if (container instanceof Fragment) {
            ((Fragment) container).requestPermissions(permissions, requestCode);
        } else if (container instanceof android.app.Fragment) {
            ((android.app.Fragment) container).requestPermissions(permissions, requestCode);
        } else {
            throw new IllegalArgumentException("container must be Activity ,or Fragment");
        }

    }


    public static class WrapperModel {
        private Object container;//可以是Activity,也可以是Fragment
        private String[] permissions;
        private PermissionCallback permissionCallback;
        private int requestCode = 8080;
        private boolean forceAccepting = false;

        public WrapperModel(Object container) {
            this.container = container;

        }

        @Override
        public String toString() {
            return "WrapperModel{" +
                    "container=" + container +
                    ", permissions=" + Arrays.toString(permissions) +
                    ", permissionCallback=" + permissionCallback +
                    ", requestCode=" + requestCode +
                    '}';
        }
    }


}
