package com.example.abhish.sms.database;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MessegeReceiveService extends Service {
    MyessegeReceive mReceiver;
    DatabaseHandler db;
    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //arrayAdapter.clear();
        do {
            Log.d("parth_sms","here 1");
            // String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
            //   "\n" + smsInboxCursor.getString(indexBody) + "\n";
            String number = smsInboxCursor.getString(indexAddress);
            String sms_body = smsInboxCursor.getString(indexBody);
            //arrayAdapter.add(str);
            db = new DatabaseHandler(this);
            Sms_format s= new Sms_format();
            MechineLearningImpl machine = new MechineLearningImpl();
            s.number=number;
            s.body=sms_body;
            s.cat= machine.processMesg(sms_body);
            Log.d("parth_sms",s.cat);
            db.addsms(s);
        } while (smsInboxCursor.moveToNext());
        db.close();
    }

    // use this as an inner class like here or as a top-level class
    public MessegeReceiveService() {
    }
    public void onCreate() {
        Toast.makeText(this,"dbhd",3).show();
        // get an instance of the receiver in your service
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mReceiver = new MyessegeReceive();
        registerReceiver(mReceiver, filter);
        Log.d("parth_sms","start");
        DataParser d = new DataParser();
        d.parseData();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String str = pref.getString("key_name", null);
        if(str == null) {
            editor.putString("key_name", "true");
            editor.commit();
        }

        str = pref.getString("key_name", null);
        if (str.equals("true")) {
            refreshSmsInbox();
            editor.putString("key_name", "false");
            editor.commit();
        }

        //DataParser dp = new DataParser();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
