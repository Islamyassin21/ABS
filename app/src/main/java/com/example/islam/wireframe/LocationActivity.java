package com.example.islam.wireframe;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;
import com.example.islam.wireframe.Model.User;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private Spinner names;
    private SharedPreferences sharedPreferences;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //      getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        back = (Button) findViewById(R.id.locationBack);
        sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        names = (Spinner) findViewById(R.id.locationSpinner);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LocationActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

//        MapFragment mapFragment = MapFragment.newInstance();
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.map, mapFragment);
//        fragmentTransaction.commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.setMyLocationEnabled(true);

        DatabaseHelper db = new DatabaseHelper(LocationActivity.this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        final ArrayList<User> list = addData();
        final ArrayList<String> spnnerlst = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            spnnerlst.add(list.get(i).getName());

        }
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spnnerlst);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        names.setAdapter(spinnerArrayAdapter);


        names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                double lat = Double.parseDouble(list.get(position).getPhone());
                double lng = Double.parseDouble(list.get(position).getPassword());
                String name = list.get(position).getName();

                location(lat, lng, name, map);

                Toast.makeText(LocationActivity.this, (int) lat + "", Toast.LENGTH_LONG).show();
//                spnnerlst.add("islam yassin " + position);
                spinnerArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        LatLng sydney = new LatLng(33.867, 151.206);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 0));
//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
//
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void location(double lat, double lng, String name, GoogleMap map) {

        LatLng sydney = new LatLng(lat, lng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
        map.addMarker(new MarkerOptions()
                .title(name)
                .position(sydney));
    }

    String[] name = new String[]{"islam yassin", "mohammed tarek", "ahmed mostafa", "ali mahmoud"};
    String[] lat = new String[]{"30.0263122", "30.0448182", "30.3495652", "29.938539"};
    String[] lng = new String[]{"31.2354112", "31.2372442", "31.2050472", "30.913365"};

    ArrayList<User> dataList = new ArrayList<User>();

    public ArrayList<User> addData() {

        for (int i = 0; i < name.length; i++) {
            User user = new User();
            user.setName(name[i]);
            user.setPhone(lat[i]);
            user.setPassword(lng[i]);
            dataList.add(user);
        }
        return dataList;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
