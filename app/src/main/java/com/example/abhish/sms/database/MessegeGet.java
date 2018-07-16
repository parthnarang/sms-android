package com.example.abhish.sms.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth.narang on 1/11/2018.
 */
public class MessegeGet implements IMessegeFetch {
    Context cont;
    public MessegeGet(Context con){
        cont = con;
    }

    public List<Sms_format> getSmsByCat(String cat){
        List<Sms_format> sms= new ArrayList<Sms_format>();
        DatabaseHandler db = new DatabaseHandler(cont);
        try{
            sms= db.getsmsByCat("1");
            for(int i=0;i<sms.size();i++)
                Log.d("parth_sms","reading from db" + sms.get(i).body);}
        catch (Exception e){
            Log.d("parth_sms",e.toString());
        }
        return sms;
    }
    public List<Sms_format> getSms(){
        List<Sms_format> sms= new ArrayList<Sms_format>();
        DatabaseHandler db = new DatabaseHandler(cont);
        try{
            sms= db.getsms();
            for(int i=0;i<sms.size();i++)
                Log.d("parth_sms","reading from db" + sms.get(i).body);}
        catch (Exception e){
            Log.d("parth_sms",e.toString());
        }
        return sms;
    }
   // public long getSmsCount(String cat){

    //}
    public void setMessageCategory(int id, String cat){

    }
}
