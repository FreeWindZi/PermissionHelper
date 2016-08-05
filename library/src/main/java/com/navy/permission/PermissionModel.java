package com.navy.permission;

import android.Manifest;

import java.security.Permission;


/**
 * Created by navychen on 16/8/5.
 */
public class PermissionModel {
    private static String CALENDAR_GROUP = Manifest.permission_group.CALENDAR;
    private static String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    private static String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;


    private static String CAMERA_GROUP = Manifest.permission_group.CAMERA;
    private static String CAMERA = Manifest.permission.CAMERA;

    private static String CONTACTS_GROUP = Manifest.permission_group.CONTACTS;
    private static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    private static String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    private static String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;

    private static String LOCATION_GROUP = Manifest.permission_group.LOCATION;
    private static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static String MICROPHONE_GROUP = Manifest.permission_group.MICROPHONE;
    private static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    private static String PHONE_GROUP = Manifest.permission_group.PHONE;
    private static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static String CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    private static String WRITE_CALL_LOG =Manifest.permission.WRITE_CALL_LOG;
    private static String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    private static String USE_SIP = Manifest.permission.USE_SIP;
    private static String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;

    private static String SENSORS_GROUP = Manifest.permission_group.SENSORS;
    private static String BODY_SENSORS = Manifest.permission.BODY_SENSORS;

    private static String SMS_GROUP = Manifest.permission_group.SMS;
    private static String SEND_SMS = Manifest.permission.SEND_SMS;
    private static String READ_SMS = Manifest.permission.READ_SMS;
    private static String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    private static String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    private static String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;


    private static String STORAGE_GROUP = Manifest.permission_group.STORAGE;
    private static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}
