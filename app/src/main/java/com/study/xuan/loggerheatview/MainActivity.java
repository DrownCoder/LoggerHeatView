package com.study.xuan.loggerheatview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.xuan.hook.HookFindView;

public class MainActivity extends AppCompatActivity {
    private TextView tvBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HookFindView.hook(this);
        tvBtn = findViewById(R.id.tv_btn);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //String name = getResources().getResourceEntryName(tvBtn.getId());
        //Log.i("xxxxxxxxxx", name);
    }
}
