package com.example.abhish.sms.database;

import com.example.abhish.sms.util.MessegeEntry;


import java.util.List;

/**
 * Created by parth.narang on 1/10/2018.
 */
public interface DatabaseHelper {
    public List<MessegeEntry> getSmsByCategory(int cat);
    public boolean addToDatabase(MessegeEntry entry);

    // public List<Sms_format> getSms();
    //public long getSmsCount(String cat);
    // public boolean setMessageCategory(int id, String cat);
}
