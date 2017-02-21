package com.example.islam.wireframe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;
import com.example.islam.wireframe.Model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button workDay, sync, download, report;
    private TextView signOut;
    CircularProgressButton circularProgressButtonSync, circularProgressButtonDownloade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();
//        getSupportActionBar().setTitle("القائمه الرئيسيه");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        new DatabaseHelper(MainActivity.this).deleteTableUser();
//        AddUser();

       //    AddShopping();


        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        Log.v("data555", sharedPreferences.getString("userName", ""));

        workDay = (Button) findViewById(R.id.mainWork);
        sync = (Button) findViewById(R.id.mainSync);
        download = (Button) findViewById(R.id.maindownload);
        report = (Button) findViewById(R.id.mainReport);
        signOut = (TextView) findViewById(R.id.logOut);

//        circularProgressButtonSync.setIndeterminateProgressMode(true);
//        circularProgressButtonDownloade.setIndeterminateProgressMode(true);
        workDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WorkDayActivity.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(i);
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (circularProgressButtonSync.getProgress() == 0) {
//
//                    circularProgressButtonSync.setProgress(30);
//                } else if (circularProgressButtonSync.getProgress() == -1) {
//                    circularProgressButtonSync.setProgress(0);
//                } else if (circularProgressButtonSync.getProgress() == 100) {
//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
//                }
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        circularProgressButtonSync.setProgress(-1);
//                    }
//                }, 3000);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("key", "1");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        circularProgressButtonDownloade.setProgress(100);
//                        Log.v("key", "2");
//
//                    }
//                }, 3000);
//
//
//                if (circularProgressButtonDownloade.getProgress() == 0) {
//
//                    circularProgressButtonDownloade.setProgress(30);
//                } else if (circularProgressButtonDownloade.getProgress() == -1) {
//                    circularProgressButtonDownloade.setProgress(0);
//                } else if (circularProgressButtonDownloade.getProgress() == 100) {
//                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(MainActivity.this, LocationActivity.class);
//                    startActivity(i);
//                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, AddShopping.class);
//                startActivity(i);

                AddShopping();
            }
        });

        signOut.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new DatabaseHelper(MainActivity.this).DeleteAllTable();
                return true;
            }
        });


    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(new Date());

    public void AddShopping() {
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);

        for (int i = 0; i < 20; i++) {
            Shapping shop = new Shapping();
            shop.setShappingCode("6546" + i + "556549");
            shop.setCostumeraddress("maadi");
            shop.setDate(date);
            shop.setStatus("جاري العمل");
            shop.setUsername("اسلام محمد ياسين");
            shop.setCostumerid("5685" + i + "65464");
            db.AddShopping(shop);
        }
    }

    public void AddUser() {
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);

        for (int i = 0; i < 200; i++) {
            User user = new User();
            user.setName("islam" + i);
            user.setPhone("6" + i + "421534" + i);
            db.AddUser(user);
        }
    }
}
