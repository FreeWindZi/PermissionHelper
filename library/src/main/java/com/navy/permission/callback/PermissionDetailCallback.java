package com.navy.permission.callback;

import com.navy.permission.PermissionHelper;

import java.sql.Wrapper;

/**
 * Created by navychen on 16/8/11.
 */
public interface PermissionDetailCallback extends PermissionCallback {

    void onPermissionExplained( String []permissions);

}
