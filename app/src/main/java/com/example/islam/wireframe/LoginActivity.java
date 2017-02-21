package com.example.islam.wireframe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
    private EditText userName, password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences sharedPreferences = getSharedPreferences("work", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //     getSupportActionBar().hide();

        userName = (EditText) findViewById(R.id.loginUserName);
        password = (EditText) findViewById(R.id.loginPassword);

        userName.setText("اسلام محمد ياسين");
        password.setText("123456");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("جاري التحقق من اسم المستخدم و كلمه المرور ...");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();

                boolean verify = true;
                if (verify) {

                    // editor.putString("userName", userName.getText().toString().trim());
                    editor.putString("userName", "اسلام محمد ياسين").commit();
                    dialog.hide();
                    startActivity(i);
                    finish();

                } else {

                    Toast.makeText(LoginActivity.this, "اسم المستخدم و كلمه المرور غير صحيحين", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
