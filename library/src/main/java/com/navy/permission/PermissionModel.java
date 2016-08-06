package com.navy.permission;

import android.Manifest;

import java.security.Permission;


/**
 * Created by navychen on 16/8/5.
 */
public class PermissionModel {
    public static String CALENDAR_GROUP = Manifest.permission_group.CALENDAR;
    public static String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;


    public static String CAMERA_GROUP = Manifest.permission_group.CAMERA;
    public static String CAMERA = Manifest.permission.CAMERA;

    public static String CONTACTS_GROUP = Manifest.permission_group.CONTACTS;
    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;

    public static String LOCATION_GROUP = Manifest.permission_group.LOCATION;
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static String MICROPHONE_GROUP = Manifest.permission_group.MICROPHONE;
    public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    public static String PHONE_GROUP = Manifest.permission_group.PHONE;
    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static String WRITE_CALL_LOG =Manifest.permission.WRITE_CALL_LOG;
    public static String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static String USE_SIP = Manifest.permission.USE_SIP;
    public static String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    public static String SENSORS_GROUP = Manifest.permission_group.SENSORS;
    public static String BODY_SENSORS = Manifest.permission.BODY_SENSORS;

    public static String SMS_GROUP = Manifest.permission_group.SMS;
    public static String SEND_SMS = Manifest.permission.SEND_SMS;
    public static String READ_SMS = Manifest.permission.READ_SMS;
    public static String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;


    public static String STORAGE_GROUP = Manifest.permission_group.STORAGE;
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}
