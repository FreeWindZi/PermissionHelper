package com.navy.permission.util;

import android.util.Log;

import com.navy.permission.PermissionHelper;

/**
 * Created by Navy on 2016/8/8.
 */
public class LogUtil {
    static final String TAG = PermissionHelper.TAG;

    /** Log Level Error **/
    public static void e(String message) {
        if (PermissionHelper.getInstance().isDebug())
            Log.e(TAG, buildLogMsg(message));
    }

    /** Log Level Warning **/
    public static void w(String message) {
        if (PermissionHelper.getInstance().isDebug())
            Log.w(TAG, buildLogMsg(message));
    }

    /** Log Level Information **/
    public static void i(String message) {
        if (PermissionHelper.getInstance().isDebug())
            Log.i(TAG, buildLogMsg(message));
    }

    /** Log Level Debug **/
    public static void d(String message) {
        if (PermissionHelper.getInstance().isDebug())
            Log.d(TAG, buildLogMsg(message));
    }

    /** Log Level Verbose **/
    public static void v(String message) {
        if (PermissionHelper.getInstance().isDebug())
            Log.v(TAG, buildLogMsg(message));
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }

}
