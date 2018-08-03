package com.example.abhish.sms.services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.abhish.sms.Tasks.impl.DataIO;
import com.example.abhish.sms.Tasks.impl.DataParser;
import com.example.abhish.sms.database.DatabaseHandler;
import com.example.abhish.sms.Tasks.impl.MessageProcessingByNavTaskImpl;
import com.example.abhish.sms.Receivers.MessageReceiver;
import com.example.abhish.sms.util.MessegeEntry;

public class MessegeReceiveService extends Service {
    MessageReceiver mReceiver;
    DatabaseHandler db;
    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        for (int i = 0; i < smsInboxCursor.getColumnCount(); i++)
        {
            Log.v("Count_maintain", smsInboxCursor.getColumnName(i).toString());
        }
        //smsInboxCursor.close();

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int person = smsInboxCursor.getColumnIndex("person");
        int type = smsInboxCursor.getColumnIndex("type");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;


        //arrayAdapter.clear();
        do {


            // String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
            //   "\n" + smsInboxCursor.getString(indexBody) + "\n";
            String number = smsInboxCursor.getString(indexAddress);
            String sms_body = smsInboxCursor.getString(indexBody);
            Log.v("abc",smsInboxCursor.getString(person) + " " + smsInboxCursor.getString(type));
            //arrayAdapter.add(str);
            db = new DatabaseHandler(this);
            MessegeEntry entry= new MessegeEntry();
            MessageProcessingByNavTaskImpl machine = new MessageProcessingByNavTaskImpl();
            entry.messegeAddress=number;
            entry.messegeBody=sms_body;
            entry.messegeCategory= Integer.parseInt(machine.processMesg(sms_body));

            db.addToDatabase(entry);
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
        mReceiver = new MessageReceiver();
        registerReceiver(mReceiver, filter);
        Log.d("parth_sms","start");
        //run parser on another thread
        final DataParser d = new DataParser();
        DataIO instance = DataIO.getInstance();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                d.parseData();
            }
        };
        instance.getHandler().post(runnable);


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
