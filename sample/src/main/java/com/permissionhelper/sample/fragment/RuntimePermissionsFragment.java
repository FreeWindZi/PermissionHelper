package com.permissionhelper.sample.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.navy.permission.PermissionHelper;
import com.navy.permission.PermissionModel;
import com.navy.permission.callback.PermissionCallback;
import com.navy.permission.callback.PermissionDetailCallback;
import com.permissionhelper.sample.FileUtil;
import com.navy.permission.util.LogUtil;
import com.permissionhelper.sample.R;

import java.io.File;

/**
 * Created by navychen on 16/8/5.
 */
public class RuntimePermissionsFragment extends Fragment implements View.OnClickListener ,
        ActivityCompat.OnRequestPermissionsResultCallback{


    private final File storageFile =  new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+
            File.separator+"permissions"+File.separator+"storage_permissions.txt");




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, null);




        root.findViewById(R.id.btn_storage_permission).setOnClickListener(this);

        root.findViewById(R.id.btn_write_storage).setOnClickListener(this);
        root.findViewById(R.id.btn_read_storage).setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btn_storage_permission:
                writeAndReadStorage();
                break;
            case R.id.btn_write_storage:

                writeStorage();
                break;
            case R.id.btn_read_storage:
                readStorage();
                break;
        }
    }

    private void writeAndReadStorage() {
        PermissionHelper.getInstance().with(this)
                .setPermissions(PermissionModel.READ_EXTERNAL_STORAGE,
                        PermissionModel.WRITE_EXTERNAL_STORAGE,
                        PermissionModel.ACCESS_COARSE_LOCATION,
                        PermissionModel.WRITE_CONTACTS,
                        PermissionModel.CAMERA,
                        PermissionModel.READ_CONTACTS)
                .setRequestCode(1000)
                .setForceAccepting(true)
                .setPermissonCallback(new PermissionDetailCallback() {

                    @Override
                    public void onPermissionExplained(String[] permission) {
                        LogUtil.d("==================onPermissionExplained " + permission);
                        getAlertDialog(permission).show();
                    }

                    @Override
                    public void onPermissionGranted() {
                        String content = "sdfassssssssssssssssssssssssssssssfsadasfaaaaaaassssssadsadasdasdasdas";
                        if (FileUtil.writeStrinToFile(storageFile.getAbsolutePath(), content)) {
                            Toast.makeText(getContext(), content + "  写入成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), content + "  写入失败", Toast.LENGTH_LONG).show();
                        }
                        String context = FileUtil.readStrinToFile(storageFile.getAbsolutePath());
                        Toast.makeText(getContext(), context + "读取成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionReject() {
                        Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_LONG).show();
                    }


                })
                .requestPermissions();
    }


    private void readStorage() {
         PermissionHelper.getInstance().with(this)
                .setPermissions(PermissionModel.READ_EXTERNAL_STORAGE)
                 .setRequestCode(1000)
                 .setForceAccepting(true)
                .setPermissonCallback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        String context = FileUtil.readStrinToFile(storageFile.getAbsolutePath());
                        Toast.makeText(getContext(), context + "读取成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionReject() {
                        Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_LONG).show();
                    }


                })
                .requestPermissions();

    }

    private void writeStorage(){
        PermissionHelper.getInstance().with(this)
                .setPermissions(PermissionModel.WRITE_EXTERNAL_STORAGE)
                .setRequestCode(100)
                .setPermissonCallback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        String content = "sdfassssssssssssssssssssssssssssssfsadasfaaaaaaassssssadsadasdasdasdas";
                        if (FileUtil.writeStrinToFile(storageFile.getAbsolutePath(), content)) {
                            Toast.makeText(getContext(), content + "  写入成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), content + "  写入失败", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onPermissionReject() {
                        Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_LONG).show();
                    }
                })
                .requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtil.d("==================onRequestPermissionsResult");
        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    public AlertDialog getAlertDialog(final String []permission) {
        AlertDialog builder = new AlertDialog.Builder(getContext())
                .setTitle("Permission Needs Explanation")
                .create();

        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionHelper.getInstance().requestAfterExplanation(permission);
            }
        });
        StringBuffer sb = new StringBuffer();
        for (String str : permission) {
            sb.append(str).append("\n");
        }
        builder.setMessage("Permissions need explanation ( \n" +  sb.toString() + ")");
        return builder;
    }


}
