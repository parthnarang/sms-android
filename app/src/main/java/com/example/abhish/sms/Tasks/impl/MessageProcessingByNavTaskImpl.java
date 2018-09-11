package com.example.abhish.sms.Tasks.impl;

import com.example.abhish.sms.Tasks.MessageProcessingByNavTask;

/**
 * Created by parth.narang on 1/12/2018.
 */
public class MessageProcessingByNavTaskImpl implements MessageProcessingByNavTask {
    static private NaiveBayes nb = new NaiveBayes();

    public String processMesg(String mesg,String number){
        int category = 0;
        category = nb.getCategory(mesg);
        return Integer.toString(category);
    }
}
