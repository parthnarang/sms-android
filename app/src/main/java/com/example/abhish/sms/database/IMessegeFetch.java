package com.example.abhish.sms.database;

import com.example.abhish.sms.util.Sms_format;

import java.util.List;

/**
 * Created by parth.narang on 1/10/2018.
 */
public interface IMessegeFetch {
    public List<Sms_format> getSmsByCat(String cat);
    public List<Sms_format> getSms();
    //public long getSmsCount(String cat);
    public void setMessageCategory(int id, String cat);
}
