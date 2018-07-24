package com.example.abhish.sms.Tasks.impl;

import com.example.abhish.sms.Tasks.MessageProcessingByNavTask;

/**
 * Created by parth.narang on 1/12/2018.
 */
public class MessageProcessingByNavTaskImpl implements MessageProcessingByNavTask {

    public String processMesg(String mesg){
        int c=0;
        DataParser d = new DataParser();
        c= d.getCategory(mesg);
        return c +"";

    }
}
