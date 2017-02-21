package com.example.islam.wireframe.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.islam.wireframe.Model.Costumer;
import com.example.islam.wireframe.Model.Shapping;
import com.example.islam.wireframe.Model.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by islam on 24/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public String CREATE_COSTUMER_TABLE;
    public String CREATE_USER_TABLE;
    public String CREATE_SHOPPING_TABLE;
    public String CREATE_SYNC_TABLE;
    public String CREATE_CHOOSE_TABLE;

    private final ArrayList<Shapping> dataListShopping = new ArrayList<>();
    private final ArrayList<Costumer> dataListCostumer = new ArrayList<>();
    private final ArrayList<User> dataListUser = new ArrayList<>();

    public DatabaseHelper(Context context) {
        super(context, Conestant.DATABASE_NAME, null, Conestant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CREATE_USER_TABLE = "CREATE TABLE " + Conestant.TABLE_USER +
                "(" +
                Conestant.USER_ID + " INTEGER PRIMARY KEY," +
                Conestant.USER_NAME + " TEXT," +
                Conestant.USER_PASSWORD + " TEXT," +
                Conestant.USER_PHONE + " TEXT," +
                Conestant.USER_KIND + " TEXT);";
        db.execSQL(CREATE_USER_TABLE);

        CREATE_COSTUMER_TABLE = "CREATE TABLE " + Conestant.TABLE_COSTUMER +
                "(" +
                Conestant.COSTUMER_ID + " INTEGER PRIMARY KEY," +
                Conestant.COSTUMER_NAME + " TEXT," +
                Conestant.COSTUMER_PHONE + " TEXT," +
                Conestant.COSTUMER_LAT + " TEXT," +
                Conestant.COSTUMER_LAN + " TEXT," +
                Conestant.COSTUMER_SIGNATURE + " TEXT);";
        db.execSQL(CREATE_COSTUMER_TABLE);

        CREATE_SHOPPING_TABLE = "CREATE TABLE " + Conestant.TABLE_SHOPPING +
                "(" +
                "_id INTEGER PRIMARY KEY," +
                Conestant.USER_NAME + " TEXT," +
                Conestant.USER_KIND + " TEXT," +
                Conestant.USER_PHONE + " TEXT," +
                Conestant.COSTUMER_ID + " TEXT," +
                Conestant.COSTUMER_NAME + " TEXT," +
                Conestant.COSTUMER_PHONE + " TEXT," +
                Conestant.COSTUMER_LAN + " TEXT," +
                Conestant.COSTUMER_SIGNATURE + " BLOB," +
                Conestant.DATE + " TEXT," +
                Conestant.COSTUMER_ADDRESS + " TEXT," +
                Conestant.COSTUMER_LAT + " TEXT," +
                Conestant.COSTUMER_PHOTO + " BLOB," +
                Conestant.SHOPPING_STATUS + " TEXT," +
                Conestant.SHOPPING_REASON + " TEXT," +
                Conestant.SHOPPING_CODE + " TEXT," +
                Conestant.SHOPPING_RELATION + " TEXT," +
                Conestant.SYNC + " TEXT);";
        db.execSQL(CREATE_SHOPPING_TABLE);

        CREATE_SYNC_TABLE = "CREATE TABLE " + Conestant.TABLE_SYNC +
                "(" +
                Conestant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Conestant.NUM_OF_UPLOAD + " TEXT," +
                Conestant.NUM_OF_DOWNLOAD + " TEXT," +
                Conestant.DATE + " TEXT);";
        db.execSQL(CREATE_SYNC_TABLE);

        CREATE_CHOOSE_TABLE = "CREATE TABLE " + Conestant.TABLE_CHOOSE + "(" +
                Conestant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Conestant.SHOPPING_CODE + " TEXT);";
        db.execSQL(CREATE_CHOOSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddChoose(Shapping shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Conestant.SHOPPING_CODE, shop.getShappingCode());
        db.insert(Conestant.TABLE_CHOOSE, null, values);
        db.close();
    }

    public ArrayList<Shapping> getChoose() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_CHOOSE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Shapping shop = new Shapping();

                shop.setShappingCode(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_CODE)));

                dataListShopping.add(shop);

            } while ((cursor.moveToNext()));
        }
        return dataListShopping;
    }

    public void deleteCostumChoose(Shapping shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestant.TABLE_CHOOSE, Conestant.SHOPPING_CODE + "=" + shop.getShappingCode(), null);
        db.close();
    }

    public void deleteTableChoose() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestant.TABLE_CHOOSE, null, null);
        db.close();
    }

    public void DeleteAllTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestant.TABLE_CHOOSE, null, null);
        db.delete(Conestant.TABLE_SHOPPING, null, null);
        db.delete(Conestant.TABLE_USER, null, null);
        db.delete(Conestant.TABLE_COSTUMER, null, null);
        db.close();
    }
    public void deleteTableUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestant.TABLE_USER, null, null);
        db.close();
    }

    public void AddCostumer(Costumer costumer) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Conestant.COSTUMER_NAME, costumer.getName());
        values.put(Conestant.COSTUMER_ID, costumer.getId());
        values.put(Conestant.COSTUMER_PHONE, costumer.getPhone());
        values.put(Conestant.COSTUMER_SIGNATURE, costumer.getSingniture());
        values.put(Conestant.COSTUMER_LAN, costumer.getLng());
        values.put(Conestant.COSTUMER_LAT, costumer.getLat());
        db.insert(Conestant.TABLE_COSTUMER, null, values);
        db.close();
    }

    public void AddUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Conestant.USER_ID, user.getId());
        values.put(Conestant.USER_NAME, user.getName());
        values.put(Conestant.USER_KIND, user.getKind());
        values.put(Conestant.USER_PASSWORD, user.getPassword());
        values.put(Conestant.USER_PHONE, user.getPhone());

        db.insert(Conestant.TABLE_USER, null, values);
        db.close();
    }

    public void AddShopping(Shapping shapping) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Conestant.SHOPPING_CODE, shapping.getShappingCode());
        values.put(Conestant.SHOPPING_STATUS, shapping.getStatus());
        values.put(Conestant.SHOPPING_RELATION, shapping.getRelation());
        values.put(Conestant.SHOPPING_REASON, shapping.getReason());
        values.put(Conestant.COSTUMER_NAME, shapping.getCostumername());
        values.put(Conestant.COSTUMER_ID, shapping.getCostumerid());
        values.put(Conestant.COSTUMER_LAT, shapping.getCostumerlat());
        values.put(Conestant.COSTUMER_LAN, shapping.getCostumerlng());
        values.put(Conestant.COSTUMER_PHONE, shapping.getCostumerphone());
        values.put(Conestant.COSTUMER_ADDRESS, shapping.getCostumeraddress());
        values.put(Conestant.COSTUMER_SIGNATURE, shapping.getCostumerSingniture());
        values.put(Conestant.USER_NAME, shapping.getUsername());
        values.put(Conestant.USER_PHONE, shapping.getUserphone());
        values.put(Conestant.USER_KIND, shapping.getUserkind());
        values.put(Conestant.SYNC, shapping.getSync());
        values.put(Conestant.DATE, shapping.getDate());
        db.insert(Conestant.TABLE_SHOPPING, null, values);

        db.close();
    }

    public void EditShopping(Shapping shapping) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Conestant.SHOPPING_STATUS, shapping.getStatus());
        values.put(Conestant.SHOPPING_RELATION, shapping.getRelation());
        values.put(Conestant.SHOPPING_REASON, shapping.getReason());
        values.put(Conestant.COSTUMER_PHOTO, shapping.getImage());
        values.put(Conestant.COSTUMER_SIGNATURE, shapping.getCostumerSingniture());
        // values.put(Conestant.SYNC, shapping.getSync());

        db.update(Conestant.TABLE_SHOPPING, values, Conestant.SHOPPING_CODE + "=" + shapping.getShappingCode(), null);

        db.close();
    }

    public String getDone(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_SHOPPING, null, Conestant.SHOPPING_STATUS + "=? AND " + Conestant.USER_NAME + "=?", new String[]{"تم التسليم", userName}, null, null, null);

        return String.valueOf(cursor.getCount());
    }

    public String getFail(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_SHOPPING, null, Conestant.SHOPPING_STATUS + "=? AND " + Conestant.USER_NAME + "=?", new String[]{"لم يتم التسليم", userName}, null, null, null);
        return String.valueOf(cursor.getCount());
    }

    public String getWorking(String userName, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_SHOPPING, null,
                Conestant.SHOPPING_STATUS + "=? AND " + Conestant.USER_NAME + "=? AND " + Conestant.DATE + "=?",
                new String[]{"جاري العمل", userName, date}, null, null, null);
        return String.valueOf(cursor.getCount());
    }

    public ArrayList<Shapping> getWorkDay(String userName, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_SHOPPING, null,
                Conestant.SHOPPING_STATUS + "=? AND " + Conestant.USER_NAME + "=? AND " + Conestant.DATE + "=?",
                new String[]{"جاري العمل", userName, date}, null, null, null);
        Log.v("cursorData", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                Shapping shop = new Shapping();

                shop.setCostumername(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_NAME)));
                shop.setCostumerlat(cursor.getFloat(cursor.getColumnIndex(Conestant.COSTUMER_LAT)));
                shop.setCostumerlng(cursor.getFloat(cursor.getColumnIndex(Conestant.COSTUMER_LAN)));
                shop.setCostumerphone(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_PHONE)));
                shop.setCostumeraddress(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_ADDRESS)));
                shop.setCostumersingniture(cursor.getBlob(cursor.getColumnIndex(Conestant.COSTUMER_SIGNATURE)));
                shop.setShappingCode(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_CODE)));
                shop.setShappingStatuse(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_STATUS)));
                shop.setShappingRelation(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_RELATION)));
                shop.setShappingreason(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_REASON)));
                shop.setUsername(cursor.getString(cursor.getColumnIndex(Conestant.USER_NAME)));
                shop.setUserphone(cursor.getString(cursor.getColumnIndex(Conestant.USER_PHONE)));
                shop.setUserkind(cursor.getString(cursor.getColumnIndex(Conestant.USER_KIND)));

                dataListShopping.add(shop);

            } while ((cursor.moveToNext()));
        }
        db.close();
        return dataListShopping;
    }

    public ArrayList<Shapping> getScanShopping(String barCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_SHOPPING, null, Conestant.SHOPPING_CODE + "=? AND " + Conestant.SHOPPING_STATUS + "=?", new String[]{barCode, "جاري العمل"}, null, null, null);
        Log.v("cursorData", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                Shapping shop = new Shapping();

                shop.setCostumername(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_NAME)));
                shop.setCostumerlat(cursor.getFloat(cursor.getColumnIndex(Conestant.COSTUMER_LAT)));
                shop.setCostumerlng(cursor.getFloat(cursor.getColumnIndex(Conestant.COSTUMER_LAN)));
                shop.setCostumerphone(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_PHONE)));
                shop.setCostumeraddress(cursor.getString(cursor.getColumnIndex(Conestant.COSTUMER_ADDRESS)));
                shop.setCostumersingniture(cursor.getBlob(cursor.getColumnIndex(Conestant.COSTUMER_SIGNATURE)));
                shop.setShappingCode(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_CODE)));
                shop.setShappingStatuse(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_STATUS)));
                shop.setShappingRelation(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_RELATION)));
                shop.setShappingreason(cursor.getString(cursor.getColumnIndex(Conestant.SHOPPING_REASON)));
                shop.setUsername(cursor.getString(cursor.getColumnIndex(Conestant.USER_NAME)));
                shop.setUserphone(cursor.getString(cursor.getColumnIndex(Conestant.USER_PHONE)));
                shop.setUserkind(cursor.getString(cursor.getColumnIndex(Conestant.USER_KIND)));

                dataListShopping.add(shop);

            } while ((cursor.moveToNext()));
        }
        db.close();
        return dataListShopping;
    }

    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestant.TABLE_USER, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(Conestant.USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(Conestant.USER_PHONE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(Conestant.USER_PASSWORD)));

                dataListUser.add(user);

            } while (cursor.moveToNext());
        }
        return dataListUser;
    }

    public boolean verificationUserAndPAss(String userName, String Password) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.query(Conestant.TABLE_USER, null, Conestant.USER_NAME + "=? AND " + Conestant.USER_PASSWORD + "=?", new String[]{userName, Password}, null, null, null);

        cursor.moveToFirst();

        Log.v("data888", String.valueOf(cursor.getCount()));
        if (cursor.getCount() <= 0) {
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }
}
