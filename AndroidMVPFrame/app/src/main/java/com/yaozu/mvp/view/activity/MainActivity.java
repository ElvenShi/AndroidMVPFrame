package com.yaozu.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yaozu.mvp.R;
import com.yaozu.mvp.test.OkHttpTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.okhttp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.okhttp:
                startActivity(new Intent(this, OkHttpTestActivity.class));
                break;
        }
    }
}
