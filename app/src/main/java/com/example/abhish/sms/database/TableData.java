package com.example.abhish.sms.database;

public class TableData {

    // Sms Table Columns names
    //public static final String KEY_ID = "id";
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "SMS";

    // Sms table name
    public static final String TABLE_SMS = "MasterTable";

    public static final String KEY_NAME = "body";
    public static final String KEY_PH_NO = "phone_number";
    public static final String DATE = "date";
    public static final String Contact_Name = "contact_name";
    public static final String STATUS = "status";
    public static final String NETWORK = "sim";
    public static final String CATEGORY = "category";


    public static final String CREATE_TABLE_SMS = "create table "
            + TableData.TABLE_SMS
            + "(" + TableData.KEY_NAME + " text not null,"
            + TableData.KEY_PH_NO + " text not null,"
            + TableData.DATE + " text not null,"
            + TableData.STATUS + " boolean not null,"
            + TableData.Contact_Name + " text not null,"
            + TableData.NETWORK + " text not null,"
            + TableData.CATEGORY + " int);";
}
