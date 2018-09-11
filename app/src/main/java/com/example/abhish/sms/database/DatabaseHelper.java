package com.example.abhish.sms.database;

import com.example.abhish.sms.util.MessageEntry;


import java.util.List;

/**
 * Created by parth.narang on 1/10/2018.
 */
public interface DatabaseHelper {
    public List<MessageEntry> getSmsByCategory(int cat);
    public boolean addToDatabase(MessageEntry entry);
    //public boolean alterDatabase(MessageEntry entry);

    // public List<Sms_format> getSms();
    //public long getSmsCount(String cat);
    // public boolean setMessageCategory(int id, String cat);
}
