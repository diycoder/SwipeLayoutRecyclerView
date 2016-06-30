package com.diy.swipelayoutrecyclerview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.diy.swipelayoutrecyclerview.R;

public class MainActivity extends AppCompatActivity {

    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simple(View view) {
        startActivity(new Intent(MainActivity.this, SimpleActivity.class));
    }

    public void Header(View view) {
        startActivity(new Intent(MainActivity.this, HeaderActivity.class));
    }

    public void HeaderAndBottom(View view) {
        startActivity(new Intent(MainActivity.this, HeaderAndBottomActivity.class));
    }
}
