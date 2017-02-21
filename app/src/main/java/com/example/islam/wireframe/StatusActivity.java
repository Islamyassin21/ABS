package com.example.islam.wireframe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;
import com.example.islam.wireframe.Model.Status;
import com.example.islam.wireframe.Model.User;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StatusActivity extends AppCompatActivity {

    private Spinner statuseSppiner, relationSppiner, reasonSppiner;
    private TextView tRelation, tReason;
    private Button next, back;


    public static final int تم_التسليم = 0;
    public static final int لم_يتم_التسليم = 1;
    private String[] statuse;
    private String[] relation;
    private String[] reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        /*******************************************************************************/
        // To remove this activity when next activity done
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) ;
                finish();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
        /******************************************************************************/

        final Bundle bundle = getIntent().getExtras();

        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("حاله الشحنه");
//        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        statuseSppiner = (Spinner) findViewById(R.id.statusFirstSpinner);
        relationSppiner = (Spinner) findViewById(R.id.statusSecondSpinner);
        reasonSppiner = (Spinner) findViewById(R.id.statusThirdSpinner);
        tRelation = (TextView) findViewById(R.id.statuseTRealation);
        tReason = (TextView) findViewById(R.id.statuseTReason);
        back = (Button) findViewById(R.id.statusBack);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StatusActivity.this, WorkDayActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        statuseSppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (statuseSppiner.getSelectedItemPosition() == 0) {
                    reasonSppiner.setEnabled(false);
                    tReason.setEnabled(false);

                    tRelation.setEnabled(true);
                    relationSppiner.setEnabled(true);

                } else {
                    tRelation.setEnabled(false);
                    relationSppiner.setEnabled(false);

                    reasonSppiner.setEnabled(true);
                    tReason.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                switch (position) {
//                    case والد:
//                        editor.putString("relation", "والد").commit();
//                        break;
//                    case والده:
//                        editor.putString("relation", "والد").commit();
//                        break;
//                    case زوجه:
//                        editor.putString("relation", "زوجه").commit();
//                        break;
//                    case زوج:
//                        editor.putString("relation", "زوج").commit();
//                        break;
//                    case ابن:
//                        editor.putString("relation", "ابن").commit();
//                        break;
//                    case ابنه:
//                        editor.putString("relation", "ابنه").commit();
//                        break;
//                    case الاخ:
//                        editor.putString("relation", "الاخ").commit();
//                        break;
//                    case الاخت:
//                        editor.putString("relation", "الاخت").commit();
//                        break;
//                    case اخو_الاب:
//                        editor.putString("relation", "اخو_الاب").commit();
//                        break;
//                    case اخو_الام:
//                        editor.putString("relation", "اخو_الام").commit();
//                        break;
//                    case اخت_الاب:
//                        editor.putString("relation", "اخت_الاب").commit();
//                        break;
//                    case اخت_الام:
//                        editor.putString("relation", "اخت_الام").commit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                switch (position) {
//                    case المكان_مغلق:
//                        editor.putString("reason", "المكان_مغلق").commit();
//
//                        break;
//                    case لا_يوجد_احد:
//                        editor.putString("reason", "لا_يوجد_احد").commit();
//                        break;
//                    case تفاصيل_العميل_خطأ:
//                        editor.putString("reason", "تفاصيل_العميل_خطأ").commit();
//                        break;
//                    case حدث_خطأ_في_البضاعه:
//                        editor.putString("reason", "حدث_خطأ_في_البضاعه").commit();
//                        break;
//                    case اخرى:
//                        editor.putString("reason", "اخرى").commit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        next = (Button) findViewById(R.id.statusNext);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Shapping shop = new Shapping();
                DatabaseHelper db = new DatabaseHelper(StatusActivity.this);
                SharedPreferences shared = getSharedPreferences("work", MODE_PRIVATE);
                String text = reasonSppiner.getSelectedItem().toString();
                Log.v("contain", text);


                sendBroadcast(new Intent("finish_Location")); // To Finish previous Activity

                int state = statuseSppiner.getSelectedItemPosition();
                if (state == 0) {
                    shop.setRelation(relationSppiner.getSelectedItem().toString());
                    Intent i = new Intent(StatusActivity.this, SignitureActivity.class);
                    shop.setStatus(statuseSppiner.getSelectedItem().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // to clear privies activity
                    i.putExtra("shoppingStatus", shop);
                    startActivity(i);

                } else {
                    new FailTask().execute((Void[]) null);
                }

                //  shop.setSync("1");
                //   shop.setShappingCode(bundle.get("shoppingCode") + "");

//                ArrayList<Shapping> code = db.getChoose();
//                for (int i = 0; i < code.size(); i++) {
//                    Shapping shop = new Shapping();
//                    shop.setStatus(shared.getString("status", ""));
//                    shop.setReason(shared.getString("reason", ""));
//                    shop.setRelation(shared.getString("relation", ""));
//                    shop.setShappingCode(code.get(i).getShappingCode());
//                    db.EditShopping(shop);
//                }


                //  Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_LONG).show();

            }
        });

        statuse = new String[]{"تم التسليم", "لم يتم التسليم"};
        relation = new String[]{"والد", "والده", "زوجه", "زوج", "ابن", "ابنه"};
        reason = new String[]{"المكان مغلق", "لا يوجد احد", "تفاصيل العميل خطأ", "حدث خطأ في البضاعه"};


        final ArrayList<Status> listReason = addReason();
        final ArrayList<String> spinnerReason = new ArrayList<String>();
        for (int i = 0; i < listReason.size(); i++) {
            spinnerReason.add(listReason.get(i).getReason());
        }
        final ArrayAdapter<String> spinnerArrayAdapterReason = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerReason);
        spinnerArrayAdapterReason.setDropDownViewResource(R.layout.spinner_item);
        reasonSppiner.setAdapter(spinnerArrayAdapterReason);
        /******************************************************************************************/
        final ArrayList<Status> listRelation = addRelation();
        final ArrayList<String> spinnerRelation = new ArrayList<String>();
        for (int i = 0; i < listRelation.size(); i++) {
            spinnerRelation.add(listRelation.get(i).getRelation());
        }
        final ArrayAdapter<String> spinnerArrayAdapterRelation = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerRelation);
        spinnerArrayAdapterRelation.setDropDownViewResource(R.layout.spinner_item);
        relationSppiner.setAdapter(spinnerArrayAdapterRelation);

        /******************************************************************************************/
        final ArrayList<Status> listStatus = addStatus();
        final ArrayList<String> spinnerStatus = new ArrayList<String>();
        for (int i = 0; i < listStatus.size(); i++) {
            spinnerStatus.add(listStatus.get(i).getStatus());
        }
        final ArrayAdapter<String> spinnerArrayAdapterStatus = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerStatus);
        spinnerArrayAdapterStatus.setDropDownViewResource(R.layout.spinner_item);
        statuseSppiner.setAdapter(spinnerArrayAdapterStatus);
    }

    ArrayList<Status> dataListStatus = new ArrayList<Status>();
    ArrayList<Status> dataListRelation = new ArrayList<Status>();
    ArrayList<Status> dataListReason = new ArrayList<Status>();

    private ArrayList<Status> addStatus() {

        for (int i = 0; i < statuse.length; i++) {
            Status statusOb = new Status();
            statusOb.setStatus(statuse[i]);
            dataListStatus.add(statusOb);
        }
        return dataListStatus;
    }

    private ArrayList<Status> addRelation() {
        for (int i = 0; i < relation.length; i++) {
            Status statusOb = new Status();
            statusOb.setRelation(relation[i]);
            dataListRelation.add(statusOb);
        }
        return dataListRelation;
    }

    private ArrayList<Status> addReason() {
        for (int i = 0; i < reason.length; i++) {
            Status statusOb = new Status();
            statusOb.setReason(reason[i]);
            dataListReason.add(statusOb);
        }
        return dataListReason;
    }


    private class FailTask extends AsyncTask<Void, Void, ArrayList<Shapping>> {

        DatabaseHelper db = new DatabaseHelper(StatusActivity.this);
        Shapping shop = new Shapping();

        @Override
        protected ArrayList<Shapping> doInBackground(Void... params) {
            return db.getChoose();
        }

        @Override
        protected void onPostExecute(ArrayList<Shapping> shappings) {
            super.onPostExecute(shappings);

            for (int i = 0; i < shappings.size(); i++) {
                shop.setShappingCode(shappings.get(i).getShappingCode());
                shop.setStatus(statuseSppiner.getSelectedItem().toString());
                shop.setReason(reasonSppiner.getSelectedItem().toString());
                db.EditShopping(shop);
                Intent k = new Intent(StatusActivity.this, WorkDayActivity.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(k);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
