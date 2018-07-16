package com.example.abhish.sms.database;

/**
 * Created by parth.narang on 1/12/2018.
 */
public class MechineLearningImpl implements MessegeLearning {

    public String processMesg(String mesg){
        int c=0;
        DataParser d = new DataParser();
        c= d.getCategory(mesg);
        return c +"";

    }
}
