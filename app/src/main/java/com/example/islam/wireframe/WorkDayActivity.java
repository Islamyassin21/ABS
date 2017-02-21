package com.example.islam.wireframe;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.ArrayAdapter.ListAdapter;
import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkDayActivity extends AppCompatActivity {

    private TextView barCode, shoppingNum;
    public ListView listView;
    private Button Scan, next, selectAll, back;
    public ListAdapter listAdapter;
    private DatabaseHelper db;
    private Shapping Shopping;
    private LocationManager locationManager;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private ProgressDialog dialog;
    private LocationListener mLocationListener;
    private String lat;
    private String lng;
    private ArrayList<Shapping> myShoppingList;
    private int tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_day);



        canToggleGPS();
        turnGPSOn();
        /*******************************************************************************/
        // To remove this activity when next activity done
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_Location")) ;
                locationManager.removeUpdates(mLocationListener);
                if (ActivityCompat.checkSelfPermission(WorkDayActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WorkDayActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_Location"));
        /******************************************************************************/

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("قائمه العمل اليومي");
//        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        barCode = (TextView) findViewById(R.id.workDayCode);
        shoppingNum = (TextView) findViewById(R.id.workdayNumber);
        listView = (ListView) findViewById(R.id.workDayList);
        Scan = (Button) findViewById(R.id.wordDayScan);
        next = (Button) findViewById(R.id.wordDayNext);
        selectAll = (Button) findViewById(R.id.wordDaySelectAll);
        back = (Button) findViewById(R.id.workBack);
//        locations = (Button) findViewById(R.id.wordDayLocation);
//        details = (Button) findViewById(R.id.wordDaydetails);

        dialog = new ProgressDialog(WorkDayActivity.this);
        dialog.setMessage("برجاء الانتظار جاري تحميل قائمه العمل اليومي ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        db = new DatabaseHelper(WorkDayActivity.this);
//        if ((myShoppingList = db.getChoose()).size() != 0) {
//            db.deleteTableChoose();
//            //   Toast.makeText(WorkDayActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
//        }


        // To get my location every 5 sec
        locationManager = (LocationManager) this.getSystemService(WorkDayActivity.this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {


                    lat = String.valueOf(location.getAltitude());
                    lng = String.valueOf(location.getLongitude());

                    Toast.makeText(WorkDayActivity.this, lat + " " + lng, Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(WorkDayActivity.this, "error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);

        // To go to the costumer location
//        locations.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                    Shapping shop = listAdapter.getItem(i);
//                    String mTitle = "مكان الشحنه";
//                    if (cb.isChecked()) {
//                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                                Uri.parse("http://maps.google.com/maps?q=loc:" + shop.getCostumerlat() + "," + shop.getCostumerlng() + " (" + mTitle + ")"));
//                        //  intent.putExtra("details", shop);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(WorkDayActivity.this, "برجاء اختيار الشحنه", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Shapping shop = listAdapter.getItem(position);
//                //  listView.getChildAt(position).setBackground(Color.BLACK);
//                //  Toast.makeText(listView.getContext(), shop.getUsername(), Toast.LENGTH_LONG).show();
//                Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
//                i.putExtra("details", shop);
//                startActivity(i);
//            }
//        });

        // Select all shopping

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkDayActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        selectAll.setTag(1);
        selectAll.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View v) {

                                             dialog = new ProgressDialog(WorkDayActivity.this);
                                             dialog.setMessage("برجاء الانتظار ...");
                                             dialog.setCancelable(false);
                                             dialog.setInverseBackgroundForced(false);
                                             dialog.show();

                                             tag = (Integer) v.getTag();
                                             if (tag == 1) {
                                                 listAdapter.selectedAll();
                                                 selectAll.setTag(0);
                                                 selectAll.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                 selectAll.setText("الغاء تحديد الكل");
                                                 dialog.cancel();
                                             } else {
                                                 listAdapter.deSelectedAll();
                                                 selectAll.setTag(1);
                                                 selectAll.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                                                 selectAll.setText("تحديد الكل");
                                                 dialog.cancel();
                                             }

//                final ListView list = listView;
//                for (int i = 0; i < listView.getCount(); i++) {
//                  //  list.setItemChecked(i, true);
//                    LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                    CheckBox ch = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                    ch.setChecked(true);
//                }
//                final int tag = (Integer) v.getTag();
//                if (tag == 1) {
//                    for (int i = 0; i < listAdapter.getCount(); i++) {
//
//                        LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                        CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                        cb.setChecked(true);
//                        listAdapter.notifyDataSetChanged();
//                        selectAll.setTag(0);
//                        selectAll.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        selectAll.setText("الغاء تحديد الكل");
//
//                    }
//                } else {
//                    for (int i = 0; i < listAdapter.getCount(); i++) {
//                        LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                        CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                        cb.setChecked(false);
//                        listAdapter.notifyDataSetChanged();
//                        selectAll.setTag(1);
//                        selectAll.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
//                        selectAll.setText("تحديد الكل");
//                    }
//                }
                                         }
                                     }

        );


//        locations.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                    Shapping shop = listAdapter.getItem(i);
//
//                    if (cb.isChecked()) {
//                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                                Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lng + "&daddr=29.97129,45.345"));
//                      //  intent.putExtra("details", shop);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });


//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//            final CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//
//            final int finalI = i;
//
//            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        Shapping shop = listAdapter.getItem(finalI);
//                        db.AddChoose(shop);
//
//                    } else {
//                        Shapping shop = listAdapter.getItem(finalI);
//                        db.deleteCostumChoose(shop);
//                    }
//                }
//            });
//        }

        next.setOnClickListener(new View.OnClickListener()

                                {

                                    private DatabaseHelper db;
                                    private Intent intent;

                                    @Override
                                    public void onClick(View v) {


                                        intent = new Intent(WorkDayActivity.this, StatusActivity.class);
                                        db = new DatabaseHelper(WorkDayActivity.this);


                                        ArrayList<Shapping> list = db.getChoose();
                                        if (list.size() == 0) {

                                            Toast.makeText(WorkDayActivity.this, "برجاء اختيار شحنه واحده على الاقل", Toast.LENGTH_LONG).show();

                                        } else {
                                            startActivity(intent);

                                            finish();
                                        }
                                    }
                                }

        );

//        details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WorkDayActivity.this, DetailsActivity.class);
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    LinearLayout itemLayout = (LinearLayout) listAdapter.getView(i, null, null);
//                    CheckBox cb = (CheckBox) itemLayout.findViewById(R.id.listSelect);
//                    Shapping shop = listAdapter.getItem(i);
//                    if (cb.isChecked()) {
//                        intent.putExtra("details", shop);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(WorkDayActivity.this, "برجاء اختيار الشحنه", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

                                            {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                                                               long id) {

                                                    Shapping shop = listAdapter.getItem(position);
                                                    Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
                                                    i.putExtra("details", shop);
                                                    startActivity(i);
                                                    return true;
                                                }
                                            }

        );


    }

    private final ArrayList<Shapping> dataListShopping = new ArrayList<>();

    public ArrayList<Shapping> getWorkDay() {

        for (int i = 0; i < 40; i++) {
            Shapping shop = new Shapping();
            shop.setCostumername("islam" + i);
            shop.setCostumerlat(i + 1.255f);
            shop.setCostumerlng(i + 2.215f);
            shop.setCostumerphone("01119155" + i + "99");
            shop.setShappingCode("246s" + i + "sd512");
            shop.setUsername("mahmoud" + i);
            shop.setUserId("13" + i);
            shop.setUserphone("03549155" + i + "29");
            dataListShopping.add(shop);
        }
        return dataListShopping;
    }

    public void scanBar2(View v) {

        try {

            //start the scanning activity from the com.google.zxing.client.android.SCAN intent

            Intent intent = new Intent(ACTION_SCAN);

            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");

            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {

            //on catch, show the download dialog

            showDialog(WorkDayActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ArrayList<Shapping> myShoppingList = new ArrayList<>();
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent

                ProgressDialog dialog = new ProgressDialog(WorkDayActivity.this);
                dialog.setMessage("جاري البحث ...");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();
                myShoppingList.clear();
                db = new DatabaseHelper(WorkDayActivity.this);
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                barCode.setText(contents);
                db.deleteTableChoose();
                ArrayList<Shapping> WorkList = db.getScanShopping(contents);
                Log.v("content", String.valueOf(WorkList.size()));
                for (int j = 0; j < WorkList.size(); j++) {

                    String mCosyumerName = WorkList.get(j).getCostumername();
                    Float mCustumerLng = WorkList.get(j).getCostumerlng();
                    Float mCostumerLat = WorkList.get(j).getCostumerlat();
                    String mCostumerPhone = WorkList.get(j).getCostumerphone();
                    String mCostumerAddress = WorkList.get(j).getCostumeraddress();
                    String mShoppingCode = WorkList.get(j).getShappingCode();
                    String mUserName = WorkList.get(j).getUsername();
                    String mUserId = WorkList.get(j).getUserId();
                    String mUserPhone = WorkList.get(j).getUserphone();

                    Shopping = new Shapping();
                    Shopping.setCostumeraddress(mCostumerAddress);
                    Shopping.setCostumername(mCosyumerName);
                    Shopping.setCostumerlng(mCustumerLng);
                    Shopping.setCostumerlat(mCostumerLat);
                    Shopping.setCostumerphone(mCostumerPhone);
                    Shopping.setShappingCode(mShoppingCode);
                    Shopping.setUsername(mUserName);
                    Shopping.setUserId(mUserId);
                    Shopping.setUserphone(mUserPhone);

                    myShoppingList.add(Shopping);
                }


                shoppingNum.setText(WorkList.size() + "");
                Log.v("WorkList", WorkList.size() + "");
                listAdapter = new ListAdapter(WorkDayActivity.this, R.layout.list_row, myShoppingList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
//                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
//                toast.show();
                dialog.hide();
            }
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);

        downloadDialog.setTitle(title);

        downloadDialog.setMessage(message);

        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {

                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                try {

                    act.startActivity(intent);

                } catch (ActivityNotFoundException anfe) {

                }

            }

        });

        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });

        return downloadDialog.show();

    }

    private class WorkTask extends AsyncTask<String, Void, ArrayList<Shapping>> {

        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<Shapping> WorkList;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        ArrayList<Shapping> myShoppingList = new ArrayList<>();

        @Override
        protected ArrayList<Shapping> doInBackground(String... params) {

            WorkList = db.getWorkDay(sharedPreferences.getString("userName", ""), date);

            for (int j = 0; j < WorkList.size(); j++) {

                String mCosyumerName = WorkList.get(j).getCostumername();
                Float mCustumerLng = WorkList.get(j).getCostumerlng();
                Float mCostumerLat = WorkList.get(j).getCostumerlat();
                String mCostumerPhone = WorkList.get(j).getCostumerphone();
                String mCostumerAddress = WorkList.get(j).getCostumeraddress();
                String mShoppingCode = WorkList.get(j).getShappingCode();
                String mUserName = WorkList.get(j).getUsername();
                String mUserId = WorkList.get(j).getUserId();
                String mUserPhone = WorkList.get(j).getUserphone();

                Shopping = new Shapping();
                Shopping.setCostumeraddress(mCostumerAddress);
                Shopping.setCostumername(mCosyumerName);
                Shopping.setCostumerlng(mCustumerLng);
                Shopping.setCostumerlat(mCostumerLat);
                Shopping.setCostumerphone(mCostumerPhone);
                Shopping.setShappingCode(mShoppingCode);
                Shopping.setUsername(mUserName);
                Shopping.setUserId(mUserId);
                Shopping.setUserphone(mUserPhone);

                myShoppingList.add(Shopping);
            }

            return myShoppingList;
        }

        @Override
        protected void onPostExecute(ArrayList<Shapping> shappings) {
            super.onPostExecute(shappings);


            shoppingNum.setText(WorkList.size() + "");

            listAdapter = new ListAdapter(WorkDayActivity.this, R.layout.list_row, shappings);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
            dialog.hide();

        }
    }

    private class CheckTask extends AsyncTask<CheckBox, Void, ArrayList<Shapping>> {

        @Override
        protected ArrayList<Shapping> doInBackground(CheckBox... params) {
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper db = new DatabaseHelper(WorkDayActivity.this);
        if ((myShoppingList = db.getChoose()).size() != 0) {
            db.deleteTableChoose();
            //   Toast.makeText(WorkDayActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
        }
        new WorkTask().execute((String[]) null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       // locationManager.removeUpdates(mLocationListener);
        turnGPSOff();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        finish();
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private boolean canToggleGPS() {
        PackageManager pacman = getPackageManager();
        PackageInfo pacInfo = null;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            return false; //package not found
        }

        if(pacInfo != null){
            for(ActivityInfo actInfo : pacInfo.receivers){
                //test if recevier is exported. if so, we can toggle GPS.
                if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
                    return true;
                }
            }
        }

        return false; //default
    }

}
