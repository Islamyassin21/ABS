package com.example.islam.wireframe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.wireframe.DataBase.DatabaseHelper;
import com.example.islam.wireframe.Model.Shapping;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

public class SignitureActivity extends AppCompatActivity {

    private DrawableView drawableView;
    DrawableViewConfig config = new DrawableViewConfig();
    private Button save, delete, attach, back;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    private TextView attachPath;
    private ImageView cancelLable;
    private String filename;
    private Shapping bundle;
    private Bitmap bitmap = null;
    private Bitmap signiture = null;
    private byte[] byteArraySigniture;
    private byte[] byteArrayPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signiture);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bundle = (Shapping) getIntent().getExtras().getSerializable("shoppingStatus");

        drawableView = (DrawableView) findViewById(R.id.paintView);
        save = (Button) findViewById(R.id.signitureSave);
        delete = (Button) findViewById(R.id.signitureDelete);
        attach = (Button) findViewById(R.id.signitureAttach);
        attachPath = (TextView) findViewById(R.id.signitureAttachFile);
        cancelLable = (ImageView) findViewById(R.id.signitureImageDelete);
        back = (Button) findViewById(R.id.signitureBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignitureActivity.this, StatusActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        cancelLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachPath.setText("");
                attachPath.setVisibility(View.INVISIBLE);
                cancelLable.setVisibility(View.INVISIBLE);
            }
        });
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setShowCanvasBounds(false); // If the view is bigger than canvas, with this the user will see the bounds (Recommended)
        config.setStrokeWidth(5);
        config.setMinZoom(1);
        config.setMaxZoom(1);
        config.setCanvasHeight(370);
        config.setCanvasWidth(420);
        config.setShowCanvasBounds(true);
//        drawableView.setMinimumHeight(370);
//        drawableView.setMinimumWidth(420);

        drawableView.setConfig(config);
        signiture = drawableView.obtainBitmap();

//        Drawable d = new BitmapDrawable(getResources(), signiture);
        Toast.makeText(SignitureActivity.this, signiture + "", Toast.LENGTH_LONG).show();
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Bitmap sign = drawableView.obtainBitmap();
                DatabaseHelper db = new DatabaseHelper(SignitureActivity.this);
                Intent i = new Intent(SignitureActivity.this, WorkDayActivity.class);

                if (bitmap != null) {
                    // To convert Bitmap to BLOB & save in DB
                    ByteArrayOutputStream streamPhoto = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamPhoto);
                    byteArrayPhoto = streamPhoto.toByteArray();
                }

                if (signiture != null) {

                    //  To get the siginture from drawerView
                    ByteArrayOutputStream streamSignture = new ByteArrayOutputStream();
                    signiture.compress(Bitmap.CompressFormat.JPEG, 100, streamSignture);
                    byteArraySigniture = streamSignture.toByteArray();
                }
                Drawable d = new BitmapDrawable(getResources(), signiture);

                boolean res = !signiture.sameAs(sign);
                if (res) {

                    new DoneTask().execute((Void[]) null);

                } else {
                    Toast.makeText(SignitureActivity.this, "برجاء الامضاء اولا ", Toast.LENGTH_LONG).show();
                }


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.clear();
            }
        });


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
            cursor.close();
            //  Toast.makeText(SignitureActivity.this, picturePath, Toast.LENGTH_LONG).show();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(picturePath, options);

            //  String pic = BitMapToString(bitmap); // To Convert Bitmap To String
            //  Toast.makeText(SignitureActivity.this, pic, Toast.LENGTH_LONG).show();

            if (filename.length() != 0) {
                attachPath.setText(filename);
                attachPath.setVisibility(View.VISIBLE);
                cancelLable.setVisibility(View.VISIBLE);
            }

        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private class DoneTask extends AsyncTask<Void, Void, ArrayList<Shapping>> {

        DatabaseHelper db = new DatabaseHelper(SignitureActivity.this);

        @Override
        protected ArrayList<Shapping> doInBackground(Void... params) {
            return db.getChoose();
        }

        @Override
        protected void onPostExecute(ArrayList<Shapping> shappings) {
            super.onPostExecute(shappings);

            for (int k = 0; k < shappings.size(); k++) {
                Shapping shop = new Shapping();
                shop.setStatus(bundle.getStatus());
                shop.setReason(bundle.getReason());
                shop.setRelation(bundle.getRelation());
                shop.setImage(byteArrayPhoto);
                shop.setCostumersingniture(byteArraySigniture);
                shop.setShappingCode(shappings.get(k).getShappingCode());
                db.EditShopping(shop);
            }
            Intent k = new Intent(SignitureActivity.this, WorkDayActivity.class);
            k.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            sendBroadcast(new Intent("finish_activity")); // To Finish previous Activity


            db.deleteTableChoose();
            startActivity(k);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
