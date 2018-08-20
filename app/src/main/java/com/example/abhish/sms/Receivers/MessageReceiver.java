package com.example.abhish.sms.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.abhish.sms.database.DatabaseHandler;
import com.example.abhish.sms.Tasks.impl.MessageProcessingByNavTaskImpl;
import com.example.abhish.sms.util.MessageEntry;

public class MessageReceiver extends BroadcastReceiver {
    public MessageReceiver() {
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
            Log.d("parth_sms", "sms body: " + sms_body);
            DatabaseHandler db = new DatabaseHandler(context);
            MessageEntry entry= new MessageEntry();
            MessageProcessingByNavTaskImpl machine = new MessageProcessingByNavTaskImpl();
            entry.messageCategory= Integer.parseInt(machine.processMesg(sms_body));
            entry.messageAddress=number;
            entry.messageBody=sms_body;
            db.addToDatabase(entry);

        }
    }
}