package com.example.abhish.sms.util;

/**
 * Created by parth.narang on 1/10/2018.
 */
public class Sms_format {
    public String cat;
    public String number;
    public String body;

    @Override
    public String toString() {
        return number + " " + body;
    }
}
