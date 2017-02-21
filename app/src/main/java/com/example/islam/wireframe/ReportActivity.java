package com.example.islam.wireframe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    private TextView done, fail, working, doneSync, failSync, hint;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("التقرير اليومي");
//        getSupportActionBar().hide();

        done = (TextView) findViewById(R.id.reportDone);
        fail = (TextView) findViewById(R.id.reportFail);
        working = (TextView) findViewById(R.id.reportWorking);
        doneSync = (TextView) findViewById(R.id.reportSync);
        failSync = (TextView) findViewById(R.id.reportNotSync);
        back = (Button) findViewById(R.id.reportBack);
        hint = (TextView) findViewById(R.id.reportHint);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        hint.setText("هذه التقارير عن اليوم الموافق (" + date + ") ");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

//        doneSync.setText("");
//        failSync.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Done().execute((Void[]) null);
        new Fail().execute((Void[]) null);
        new Working().execute((Void[]) null);
    }

    private class Done extends AsyncTask<Void, Void, String> {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);

        @Override
        protected String doInBackground(Void... params) {
            return db.getDone(sharedPreferences.getString("userName", ""));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            done.setText(s);
        }
    }

    private class Fail extends AsyncTask<Void, Void, String> {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);

        @Override
        protected String doInBackground(Void... params) {
            return db.getFail(sharedPreferences.getString("userName", ""));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fail.setText(s);
        }
    }

    private class Working extends AsyncTask<Void, Void, String> {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());


        @Override
        protected String doInBackground(Void... params) {
            return db.getWorking(sharedPreferences.getString("userName", ""), date);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            working.setText(s);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

