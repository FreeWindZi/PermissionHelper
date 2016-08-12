package com.navy.permission.callback;

/**
 * Created by navychen on 16/8/11.
 */
public interface PermissionDetailCallback extends PermissionCallback {

    void onPermissionExplained( String []permissions);

}
