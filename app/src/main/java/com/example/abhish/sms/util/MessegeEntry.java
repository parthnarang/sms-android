package com.example.abhish.sms.util;

import android.content.ContentValues;

import com.example.abhish.sms.database.TableData;

public class MessegeEntry {

    public static final int MESSEGE_CATEGORY_PERSONAL = 0;
    public static final int MESSEGE_CATEGORY_OTP = 1;
    public static final int MESSEGE_CATEGORY_BANK = 2;
    public static final int MESSEGE_CATEGORY_PROMOTIONS = 3;
    public static final int MESSEGE_CATEGORY_SPAM = 4;;


    public String messegeAddress;
    public String messegeBody;
    public int messegeCategory;
    private String contactName;
    private String time;
    private String date;
    private boolean readStatus;
    private String network;

    public MessegeEntry(){
    contactName="abc";
    time="abc";
    date="abc";
    readStatus=false;
    network="abc";
    }

    public MessegeEntry(String messegeAddress, String messegeBody, int messegeCategory,
                        String contactName, String time, String date, boolean readStatus, String network) {
        this.messegeAddress = messegeAddress;
        this.messegeBody = messegeBody;
        this.messegeCategory = messegeCategory;
        this.contactName = contactName;
        this.time = time;
        this.date = date;
        this.readStatus = readStatus;
        this.network = network;

    }
    public String getBody(){
        return messegeBody;
    }
    public String getMessegeAddress(){
        return messegeAddress;
    }
    public String getContactName(){
        return contactName;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public boolean getStatus(){
        return readStatus;
    }
    public int getCategoty(){
        return messegeCategory;
    }

    public String getNetwork() {
        return network;
    }

    public ContentValues getContentValues(MessegeEntry entry){

        ContentValues values = new ContentValues();
        values.put(TableData.KEY_NAME, entry.getBody());
        values.put(TableData.KEY_PH_NO, entry.getMessegeAddress());
        values.put(TableData.DATE, entry.getDate());
        values.put(TableData.TIME, entry.getTime());
        values.put(TableData.Contact_Name, entry.getContactName());
        values.put(TableData.NETWORK, entry.getNetwork());
        values.put(TableData.CATEGORY, entry.getCategoty());

        return values;
    }

}