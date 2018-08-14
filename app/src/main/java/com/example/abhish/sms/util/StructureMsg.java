package com.example.abhish.sms.util;

/**
 * Created by abhi.sh on 1/12/2018.
 */

public class StructureMsg {
    private String sender;
    private String detail;

    public StructureMsg(String sender, String detail){
        this.sender = sender;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public String getSender() {
        return sender;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
