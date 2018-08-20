package com.example.abhish.sms.Tasks.impl;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.abhish.sms.database.DatabaseHandler;
import com.example.abhish.sms.services.MessegeReceiveService;
import com.example.abhish.sms.ui.Activities.MainActivity;
import com.example.abhish.sms.util.MessegeEntry;

public class BackgroundMessageProcessingAsyncTask extends AsyncTask<Void,Void,Void>{


    private String resp;
        ProgressDialog progressDialog;
    DatabaseHandler db;

    private final Context mContext;

    public BackgroundMessageProcessingAsyncTask(final Context context,DatabaseHandler db) {
        mContext = context;
        this.db=db;
    }
    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if(contactName!=null)
            Log.d("abcd",contactName +"\n");
        if(contactName == null)
            contactName ="abc";
        return contactName;
    }


        protected Void doInBackground(Void... params) {
            Log.d("parth_test","called here async");
            ContentResolver contentResolver = mContext.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

            for (int i = 0; i < smsInboxCursor.getColumnCount(); i++)
            {
                Log.v("Count_maintain", smsInboxCursor.getColumnName(i).toString());
            }
            Log.d("parth_test",""+smsInboxCursor.getColumnCount()+"");

            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
            int read = smsInboxCursor.getColumnIndex("read");
            int date = smsInboxCursor.getColumnIndex("date");
            if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {

            } else {

                //arrayAdapter.clear();
                do {

                    // String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    //   "\n" + smsInboxCursor.getString(indexBody) + "\n";
                    String number = smsInboxCursor.getString(indexAddress);
                    String sms_body = smsInboxCursor.getString(indexBody);
                    String read_status = smsInboxCursor.getString(read);
                    String date_time = smsInboxCursor.getString(date);

                    //Log.v("abc",smsInboxCursor.getString(read) + " " + smsInboxCursor.getString(date));
                    //arrayAdapter.add(str);
                    db = new DatabaseHandler(mContext);
                    MessegeEntry entry = new MessegeEntry(mContext);
                    MessageProcessingByNavTaskImpl machine = new MessageProcessingByNavTaskImpl();
                    entry.messegeAddress = number;
                    entry.messegeBody = sms_body;
                    entry.date = date_time;
                    entry.contactName = getContactName(mContext,number);
                    entry.readStatus = Boolean.parseBoolean(read_status);
                    entry.messegeCategory = Integer.parseInt(machine.processMesg(sms_body));

                    db.addToDatabase(entry);
                } while (smsInboxCursor.moveToNext());
                db.close();
            }
            return null;
        }



        protected void onPostExecute(Void result) {
            // execution of result of Long time consuming operation
           // progressDialog.dismiss();
        //    finalResult.setText(result);
        }



        protected void onPreExecute() {
           // mContext.loadall_msgFragment();
          //  progressDialog = ProgressDialog.show(MainActivity.this,
              //      "ProgressDialog",
                //    "Wait for "+time.getText().toString()+ " seconds");
        }


        protected void onProgressUpdate(String... text) {
        //    finalResult.setText(text[0]);

        }
    }

