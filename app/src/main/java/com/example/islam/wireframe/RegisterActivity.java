package com.example.islam.wireframe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, phone, password;
    private Spinner kind;
    private Button save;
    private User user;
    private DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = new User();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("التسجيل");
        name = (EditText) findViewById(R.id.registerName);
        phone = (EditText) findViewById(R.id.registerPhone);
        password = (EditText) findViewById(R.id.registerPassword);
        kind = (Spinner) findViewById(R.id.registerKind);
        save = (Button) findViewById(R.id.registerSave);

        user.setName(name.getText().toString().trim());
        user.setPhone(phone.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());

        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", 999999).commit();


        kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    user.setKind("manager");
                    editor.putString("kind", "manager").commit();
                } else {
                    user.setKind("user");
                    editor.putString("kind", "user").commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences preferences = getSharedPreferences("work", MODE_PRIVATE);
                final SharedPreferences.Editor edit = preferences.edit();
                if (name.getText().length() != 0 || password.getText().length() != 0 || phone.getText().length() != 0) {
                    boolean verify = db.verificationUserAndPAss(name.getText().toString().trim(), password.getText().toString().trim());

                    if (!verify) {
                        if ((preferences.getString("kind", "manager")) == "manager") {

                            View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.cunfairm, null);
                            final EditText confarm = (EditText) view.findViewById(R.id.confairmPassword);

                            final AlertDialog.Builder Mail = new AlertDialog.Builder(RegisterActivity.this);
                            Mail.setView(view);
                            Mail.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int key = preferences.getInt("key", 123);
                                    Log.v("data1", String.valueOf(key));
                                    if (Integer.valueOf(confarm.getText().toString().trim()) == key) {
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        edit.putString("userName", name.getText().toString().trim());
                                        edit.putString("password", password.getText().toString().trim());
                                        edit.commit();
                                        db.AddUser(user);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "الرقم السري غير صحيح", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            Mail.setCancelable(true);
                            Mail.show();
                        } else {
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            edit.putString("userName", name.getText().toString().trim()).commit();
                            edit.putString("password", password.getText().toString().trim()).commit();
                            db.AddUser(user);
                            startActivity(i);
                        }// if manager
                    } else {
                        Toast.makeText(getApplicationContext(), "هذا المستخدم موجود مسبقا", Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "يجب ملأ جميع الحقول ", Toast.LENGTH_LONG).show();

                }// if empty
            }
        });

    }
}
