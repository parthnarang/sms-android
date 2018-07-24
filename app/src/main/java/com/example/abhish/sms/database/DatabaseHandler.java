package com.example.abhish.sms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.abhish.sms.util.Sms_format;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth.narang on 1/10/2018.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SMS";

    // Sms table name
    private static final String TABLE_SMS = "MasterTable";

    // Sms Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "body";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_CATEGORY = "category";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }

    public void addsms(Sms_format sms) {
        String ph_no= sms.number;
        String body= sms.body;
        String cat= sms.cat;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, body); // Messege body
        values.put(KEY_PH_NO, ph_no);// Contact  Number
        values.put(KEY_CATEGORY, cat);// Messege Cat

        // Inserting Row
        db.insert(TABLE_SMS, null, values);
        db.close(); // Closing database connection
    }
    public List<Sms_format> getsmsByCat(String cat) {
        List<Sms_format> sms = new ArrayList<Sms_format>();
        String selectQuery = "SELECT  * FROM " + TABLE_SMS + " WHERE " + KEY_CATEGORY + " = '" + cat + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Sms_format contact = new Sms_format();
                contact.body = cursor.getString(1);
                contact.number = cursor.getString(2);
                // Adding contact to list
                sms.add(contact);
            } while (cursor.moveToNext());
        }
db.close();
        return sms;
    }
    public List<Sms_format> getsms() {
        List<Sms_format> sms = new ArrayList<Sms_format>();
        String selectQuery = "SELECT  * FROM " + TABLE_SMS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Sms_format contact = new Sms_format();
                contact.body = cursor.getString(1);
                contact.number = cursor.getString(2);
                // Adding contact to list
                sms.add(contact);
            } while (cursor.moveToNext());
        }
db.close();
        return sms;
    }
}

