package com.permissionhelper.sample.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.permissionhelper.sample.MainActivity;
import com.permissionhelper.sample.R;

/**
 * Created by navychen on 16/8/5.
 */
public class RuntimePermissionsFragment extends Fragment implements View.OnClickListener {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, null);



        root.findViewById(R.id.btn_camera_permission).setOnClickListener(this);
        root.findViewById(R.id.btn_contacts_permission).setOnClickListener(this);
        root.findViewById(R.id.btn_storage_permission).setOnClickListener(this);


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
        }
    }
}
