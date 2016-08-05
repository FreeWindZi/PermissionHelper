package com.permissionhelper.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.permissionhelper.sample.fragment.RuntimePermissionsFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContentFrameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mContentFrameLayout = (FrameLayout) findViewById(R.id.content_frameLayout);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frameLayout, new RuntimePermissionsFragment())
                    .commit();

        }
    }
}
