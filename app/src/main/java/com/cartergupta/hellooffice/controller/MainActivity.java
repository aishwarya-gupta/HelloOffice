package com.cartergupta.hellooffice.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.cartergupta.hellooffice.R;
import com.cartergupta.hellooffice.database.MonitorMeDatabaseHelper;
import com.cartergupta.hellooffice.handler.MainHandler;

public class MainActivity extends AppCompatActivity {

    MainHandler handler = new MainHandler(this);
    MonitorMeDatabaseHelper db;
    RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CLASS : ","MainActivity");
        Log.i("METHOD : ","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MonitorMeDatabaseHelper(this);
        view = (RelativeLayout)findViewById(R.id.activity_main);
    }

    public void inOutToggleButtonMainActivity(View view) {
        Log.i("CLASS : ","MainActivity");
        Log.i("METHOD : ","inOutToggleButtonMainActivity");
        boolean in = ((ToggleButton) view).isChecked();
        handler.inOutToggleButtonMainHandler(in,this.view);
    }
}
