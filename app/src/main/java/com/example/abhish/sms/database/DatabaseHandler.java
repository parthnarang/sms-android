package com.example.abhish.sms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.abhish.sms.util.MessageEntry;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhish.sms.database.TableData.CREATE_TABLE_SMS;
import static com.example.abhish.sms.database.TableData.TABLE_SMS;

/**
 * Created by parth.narang on 1/10/2018.
 */
public class DatabaseHandler extends SQLiteOpenHelper implements DatabaseHelper {
    // All Static variables
    // Database Version
    public String KEY_CATEGORY= "category";

    public DatabaseHandler(Context context) {
        super(context, TableData.DATABASE_NAME, null, TableData.DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("curson",CREATE_TABLE_SMS);
        try{
            db.execSQL(TableData.CREATE_TABLE_SMS);}
        catch (Exception e){
            Log.d("parth",e.toString());
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }
    public boolean addToDatabase(MessageEntry entry) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = entry.getContentValues(entry);

        // Inserting Row
        db.insert(TABLE_SMS, null, values);
        db.close(); // Closing database connection
        return true;
    }

    public List<MessageEntry> getSmsByCategory(int cat) {
        List<MessageEntry> sms = new ArrayList<MessageEntry>();
        String selectQuery = "SELECT  * FROM " + TABLE_SMS + " WHERE " + KEY_CATEGORY + " = '" + cat + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("curson",""+cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                MessageEntry entry = new MessageEntry();
                entry.messageBody = cursor.getString(0);
                entry.messageAddress = cursor.getString(1);
                // Adding contact to list
                sms.add(entry);
            } while (cursor.moveToNext());
        }
        db.close();
        return sms;
    }
 /*   public List<Sms_format> getsms() {
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
    }*/
}

