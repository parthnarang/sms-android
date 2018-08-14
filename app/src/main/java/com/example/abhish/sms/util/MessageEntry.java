package com.example.abhish.sms.util;

import android.content.ContentValues;
import android.content.Context;

import com.example.abhish.sms.database.TableData;

public class MessageEntry {

    public static final int MESSEGE_CATEGORY_PERSONAL = 0;
    public static final int MESSEGE_CATEGORY_OTP = 1;
    public static final int MESSEGE_CATEGORY_BANK = 2;
    public static final int MESSEGE_CATEGORY_PROMOTIONS = 3;
    public static final int MESSEGE_CATEGORY_SPAM = 4;
    ;


    public String messageAddress;
    public String messageBody;
    public int messageCategory;
    public String contactName;
    public String time;
    public String date;
    public boolean readStatus;
    public String network;
    private Context con;

    public MessageEntry() {
        contactName = "abc";
        time = "abc";
        date = "abc";
        readStatus = false;
        network = "abc";

    }

    public MessageEntry(Context con) {
        this.con = con;
        contactName = "abc";
        time = "abc";
        date = "abc";
        readStatus = false;
        network = "abc";
    }


    public MessageEntry(String messageAddress, String messageBody, int messageCategory,
                        String contactName, String time, String date, boolean readStatus, String network) {
        this.messageAddress = messageAddress;
        this.messageBody = messageBody;
        this.messageCategory = messageCategory;
        this.contactName = contactName;
        this.time = time;
        this.date = date;
        this.readStatus = readStatus;
        this.network = network;

    }

    public String getBody() {
        return messageBody;
    }

    public String getMessageAddress()  {
        return messageAddress;
    }

    public String getContactName() {
        return contactName;

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean getStatus() {
        return readStatus;
    }

    public int getCategory() {
        return messageCategory;
    }

    public String getNetwork() {
        return network;
    }

    public ContentValues getContentValues(MessageEntry entry) {

        ContentValues values = new ContentValues();
        values.put(TableData.KEY_NAME, entry.getBody());
        values.put(TableData.KEY_PH_NO, entry.getMessageAddress());
        values.put(TableData.DATE, entry.getDate());
        values.put(TableData.STATUS, entry.getStatus());
        values.put(TableData.Contact_Name, entry.getContactName());
        values.put(TableData.NETWORK, entry.getNetwork());
        values.put(TableData.CATEGORY, entry.getCategory());

        return values;
    }

}
