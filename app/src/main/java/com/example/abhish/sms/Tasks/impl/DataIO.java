package com.example.abhish.sms.Tasks.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class DataIO extends HandlerThread {
    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final int QUIT = 3;
    DataParser dataParser = new DataParser();

    static private Handler mHandler;

    public DataIO() {
        super("DataIO");
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case READ:
                        break;
                    case WRITE: //Write Data
                        break;
                    case QUIT: getLooper().quit();//make this quitSafely()
                        break;
                }
            }
        };
    }

    public static Handler getHandler(){
        return mHandler;
    }
}

