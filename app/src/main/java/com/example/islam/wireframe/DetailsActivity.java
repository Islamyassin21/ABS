package com.example.islam.wireframe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.islam.wireframe.Model.Shapping;

public class DetailsActivity extends AppCompatActivity {

    public TextView costumerName;
    public TextView costumerId;
    public TextView shoppingId;
    public TextView costumerAddress;
    public TextView userId;
    public TextView phone;
    public TextView userName;
    public Button map, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("تفاصيل الشحنه");
//        getSupportActionBar().hide();
        Shapping bundle = (Shapping) getIntent().getExtras().getSerializable("details");

        costumerName = (TextView) findViewById(R.id.detailsName);
        costumerId = (TextView) findViewById(R.id.detailsCustomerCode);
        shoppingId = (TextView) findViewById(R.id.detailsChappingCoder);
        costumerAddress = (TextView) findViewById(R.id.detailsAddress);
        userName = (TextView) findViewById(R.id.detailsDelivaryNome);
        userId = (TextView) findViewById(R.id.detailsDelivaryCode);
        phone = (TextView) findViewById(R.id.detailsPhone);
        map = (Button) findViewById(R.id.detailsMap);
        back = (Button) findViewById(R.id.detailsBack);

        costumerName.setText(bundle.getCostumername());
        costumerId.setText(bundle.getCostumerid());
        shoppingId.setText(bundle.getShappingCode());
        costumerAddress.setText(bundle.getCostumeraddress());
        userName.setText(bundle.getUsername());
        userId.setText(bundle.getUserId());
        phone.setText(bundle.getUserphone());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, WorkDayActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
