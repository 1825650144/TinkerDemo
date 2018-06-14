package com.nm.tinkerdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.nm.tinkerdemo.R;
import com.nm.tinkerdemo.base.BaseApplication;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.getInstance().addActivity(this);

        Button btn01 = findViewById(R.id.btn_01);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgs();
            }
        });

        Button btnLoadPatch = findViewById(R.id.btn_loadPatch);
        btnLoadPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPatch();
            }
        });
    }

    public void loadPatch() {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    public void mgs() {
        Intent it = new Intent(this,TestActivity2.class);
        startActivity(it);
    }

}
