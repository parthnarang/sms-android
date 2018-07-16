package com.example.abhish.sms.database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MyessegeReceive extends BroadcastReceiver {
    public MyessegeReceive() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("parth_sms", "received");
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_body = "";
        String number = "";
        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                number = smsm[i].getOriginatingAddress();
                sms_body = smsm[i].getMessageBody().toString();

            }

            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
           // Toast.makeText(context, sms_str, 4).show();
            Log.d("parth_sms", sms_body);
            DatabaseHandler db = new DatabaseHandler(context);
            Sms_format s= new Sms_format();
            MechineLearningImpl machine = new MechineLearningImpl();
            s.cat= machine.processMesg(sms_body);
            s.number=number;
            s.body=sms_body;
            db.addsms(s);

        }
    }
}