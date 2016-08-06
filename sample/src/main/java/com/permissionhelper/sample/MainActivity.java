package com.permissionhelper.sample;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.navy.permission.PermissionHelper;
import com.navy.permission.util.PermissionUtil;
import com.permissionhelper.sample.fragment.RuntimePermissionsFragment;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            changeFragment( new RuntimePermissionsFragment(), null);
        }


    }

    public void changeFragment(android.support.v4.app.Fragment fragment, String name ) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frameLayout, fragment)
                .addToBackStack(name)
                .commit();

    }


}
