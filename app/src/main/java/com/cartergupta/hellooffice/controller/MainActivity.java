package com.cartergupta.hellooffice.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.cartergupta.hellooffice.R;
import com.cartergupta.hellooffice.database.MonitorMeDatabaseHelper;
import com.cartergupta.hellooffice.handler.MainHandler;

public class MainActivity extends AppCompatActivity {

    MainHandler handler = new MainHandler(this);
    MonitorMeDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MonitorMeDatabaseHelper(this);
    }

    public void toggleMain(View view) {
        boolean in = ((ToggleButton) view).isChecked();
        handler.mainToggleButton(in, view);
    }
}
