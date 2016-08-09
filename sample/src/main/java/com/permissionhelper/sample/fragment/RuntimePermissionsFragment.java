package com.permissionhelper.sample.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.navy.permission.PermissionHelperTest;
import com.navy.permission.PermissionModel;
import com.navy.permission.callback.PermissonCallback;
import com.navy.permission.util.FileUtil;
import com.permissionhelper.sample.MainActivity;
import com.permissionhelper.sample.R;

import java.io.File;

/**
 * Created by navychen on 16/8/5.
 */
public class RuntimePermissionsFragment extends Fragment implements View.OnClickListener {


    private final File storageFile =  new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+
            File.separator+"permissions"+File.separator+"storage_permissions.txt");

    PermissionHelperTest readPermission;
    PermissionHelperTest writePermission;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, null);



        root.findViewById(R.id.btn_camera_permission).setOnClickListener(this);
        root.findViewById(R.id.btn_contacts_permission).setOnClickListener(this);
        root.findViewById(R.id.btn_storage_permission).setOnClickListener(this);

        root.findViewById(R.id.btn_write_storage).setOnClickListener(this);
        root.findViewById(R.id.btn_read_storage).setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_camera_permission:
                break;
            case R.id.btn_contacts_permission:
                break;
            case R.id.btn_storage_permission:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).changeFragment(new StoragePermissionsFragment(), "storage");
                }
                break;
            case R.id.btn_write_storage:

                writeStorage();
                break;
            case R.id.btn_read_storage:
                readStorage();
                break;
        }
    }



    private void readStorage() {
        readPermission = new PermissionHelperTest.WrapperModel(this)
                .setPermissions(PermissionModel.READ_EXTERNAL_STORAGE)
                .setBaseCallback(new PermissonCallback() {
                    @Override
                    public void onPermissionGranted() {
                        String context = FileUtil.readStrinToFile(storageFile.getAbsolutePath());
                        Toast.makeText(getContext(), context, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionReject() {
                        Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_LONG).show();
                    }


                })
                .build();
        readPermission.requestPermissions();
    }

    private void writeStorage(){
        readPermission = new PermissionHelperTest.WrapperModel(this)
                .setPermissions(PermissionModel.WRITE_EXTERNAL_STORAGE)
                .setRequestCode(100)
                .setBaseCallback(new PermissonCallback() {
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
                .build();
        readPermission.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (readPermission != null) {
            readPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (writePermission != null) {
            writePermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
