package com.example.islam.wireframe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;

public class AddShopping extends AppCompatActivity {

    private EditText name, phone, code, address, lat, lng, userName;
    private TextView scanFormat;
    private DatabaseHelper db = new DatabaseHelper(AddShopping.this);
    private Button add, scan;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping);

        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);

        name = (EditText) findViewById(R.id.shopCostumerName);
        phone = (EditText) findViewById(R.id.shopCostumerPhone);
        code = (EditText) findViewById(R.id.shopCode);
        address = (EditText) findViewById(R.id.shopCostumerAddress);
        lat = (EditText) findViewById(R.id.shopCostumerLat);
        lng = (EditText) findViewById(R.id.shopCostumerLng);
        userName = (EditText) findViewById(R.id.userName);
        scanFormat = (TextView) findViewById(R.id.scanFormat);
        scan = (Button) findViewById(R.id.scan);
        add = (Button) findViewById(R.id.addShop);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shapping shapping = new Shapping();
                shapping.setUsername(userName.getText().toString().trim());
                shapping.setCostumername(name.getText().toString().trim());
                shapping.setCostumerphone(phone.getText().toString().trim());
                shapping.setCostumeraddress(address.getText().toString().trim());
                shapping.setCostumerlat(Float.parseFloat(lat.getText().toString().trim()));
                shapping.setCostumerlng(Float.parseFloat(lng.getText().toString().trim()));
                shapping.setShappingCode(scanFormat.getText().toString().trim());
                shapping.setDate(code.getText().toString().trim());
                shapping.setStatus("جاري العمل");
                shapping.setSync("لم تتم المزامنه");

                db.AddShopping(shapping);
            }
        });

    }

    public void scanBar(View v) {

        try {

            //start the scanning activity from the com.google.zxing.client.android.SCAN intent

            Intent intent = new Intent(ACTION_SCAN);

            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");

            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {

            //on catch, show the download dialog

            //showDialog(AddShopping.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                scanFormat.setText(contents);
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
