package com.example.abhish.sms.Tasks.impl;

/**
 * Created by parth.narang on 1/12/2018.
 */
import android.util.Log;

import com.example.abhish.sms.util.PreprocessingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;


public class DataParser {
    static HashMap<String, Integer> category1 = new HashMap<String, Integer>();
    static HashMap<String, Integer> category2 = new HashMap<String, Integer>();
    static HashMap<String, Integer> category3 = new HashMap<String, Integer>();
    static HashMap<String, Integer> category4 = new HashMap<String, Integer>();
    static HashMap<String, Integer> category5 = new HashMap<String, Integer>();
    static HashMap<String, Integer> tcount = new HashMap<String, Integer>();
    File json = new File("storage/emulated/0/data.json");
    ;

    private String returnJsonString() {
        FileInputStream stream = null;
        String jsonStr = null;
        try {
            stream = new FileInputStream(json);
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            jsonStr = Charset.defaultCharset().decode(bb).toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonStr;
    }

     public void parseData() {
        //populate hashmapobj;
        String jsonStr = returnJsonString();
        try {
            JSONObject jobject = new JSONObject(jsonStr);
            tcount = getMap((JSONObject) jobject.get("COUNT"));
            Log.d("parth_sms", tcount + " ");
            JSONArray feature = (JSONArray) jobject.getJSONArray("FEATURE");
            JSONObject frequency = (JSONObject) jobject.get("FREQUENCY");
            category1 = getMap((JSONObject) frequency.get("0"));
            category2 = getMap((JSONObject) frequency.get("1"));
            category3 = getMap((JSONObject) frequency.get("2"));
            category4 = getMap((JSONObject) frequency.get("3"));
            category5 = getMap((JSONObject) frequency.get("4"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private static HashMap<String, Integer> getMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, Integer> map = new HashMap<>();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            int value = (Integer) jsonObject.get(key);
            map.put(key, value);
        }
        return map;
    }

    static int NaiveBayes(String msg) {
        String[] msgparts = msg.split(" ");
        //category1
        double probc1 = 1.0;
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if (category1.containsKey(word)) {
                probc1 *= ((double) category1.get(word)) / (double) (tcount.get("0"));
            } else {
                Log.d("parth_sms", tcount + " " + tcount.size() + " " + tcount.get("0") + " ");
                probc1 *= 1.0 / (double) (tcount.get("0"));
            }
        }

        //category2
        double probc2 = 1.0;
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if (category2.containsKey(word)) {
                probc2 *= ((double) category2.get(word)) / (double) (tcount.get("1"));
            } else {
                probc2 *= 1.0 / (double) (tcount.get("1"));
            }

        }
        //category3
        double probc3 = 1.0;
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if (category3.containsKey(word)) {
                probc3 *= ((double) category3.get(word)) / (double) (tcount.get("2"));
            } else {
                probc3 *= 1.0 / (double) (tcount.get("2"));
            }
        }

        int category = 0;
        if (probc1 >= probc2) {
            if (probc1 >= probc3) {
                category = 0;
            } else {
                category = 2;
            }
        } else {
            if (probc2 >= probc3) {
                category = 1;
            } else {
                category = 2;
            }
        }

        //update values in hashmap to account for the new words
        if (category == 0) {
            for (int i = 0; i < msgparts.length; ++i) {
                String word = msgparts[i];
                if (category1.containsKey(word)) {
                    int init_val = category1.get(word);
                    category1.put(word, init_val + 1);
                    init_val = tcount.get("0");
                    tcount.put("0", init_val + 1);
                } else {
                    int init_val;
                    category1.put(word, 1);
                    init_val = tcount.get("0");
                    tcount.put("0", init_val + 1);
                }
            }
        } else if (category == 1) {
            for (int i = 0; i < msgparts.length; ++i) {
                String word = msgparts[i];
                if (category2.containsKey(word)) {
                    int init_val = category2.get(word);
                    category2.put(word, init_val + 1);
                    init_val = tcount.get("1");
                    tcount.put("1", init_val + 1);
                } else {
                    int init_val;
                    category2.put(word, 1);
                    init_val = tcount.get("1");
                    tcount.put("1", init_val + 1);
                }
            }
        } else {
            for (int i = 0; i < msgparts.length; ++i) {
                String word = msgparts[i];
                if (category3.containsKey(word)) {
                    int init_val = category3.get(word);
                    category3.put(word, init_val + 1);
                    init_val = tcount.get("2");
                    tcount.put("2", init_val + 1);
                } else {
                    int init_val;
                    category3.put(word, 1);
                    init_val = tcount.get("2");
                    tcount.put("2", init_val + 1);
                }
            }
        }

        return category;
    }

    @SuppressWarnings("static-access")
    public int getCategory(String txt) {
        // TODO Auto-generated method stub
        //parseData
        //parseData();

        // data cleaning
       // PreprocessingUtils pp = new PreprocessingUtils();
        String msg = PreprocessingUtils.cleanData(txt);

        // prediction
        int pred = NaiveBayes(msg);
        return pred;
    }

    public HashMap<String, Integer> getCategoryMap(int cat) {
        switch(cat){
            case 0:
                return category1;
            case 1:
                return category2;
            case 2:
                return category3;
            case 3:
                return category4;
            case 4:
                return category5;

        }
       return null;
    }

    public void writeToJson(String msg, int cat) {
     HashMap<String,Integer> map = getCategoryMap(cat);
       // int count = tcount.get(cat);
       // tcount.put(String.valueOf(cat), ++count);
        String[] msgparts = msg.split(" ");
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if(map.containsKey(word)){
                int cou= map.get(word);
                map.put(word, ++cou);
            }
            else
                map.put(word,1);
        }
        String jsonStr = returnJsonString();
        try {
            JSONObject jobject = new JSONObject(jsonStr);
            JSONObject frequency = (JSONObject) jobject.get("FREQUENCY");
            JSONObject category = new JSONObject(map);
            frequency.put(String.valueOf(cat),category);
            jobject.put("FREQUENCY",frequency);
            String path = "storage/emulated/0/datar.json";

            ObjectOutputStream outputStream = null;
                outputStream = new ObjectOutputStream(new FileOutputStream(path));
                System.out.println("Start Writings");
                outputStream.writeObject(jobject.toString());
                outputStream.flush();
                outputStream.close();
        }
        catch (Exception e){
            Log.d("parth",e.toString());
        }
    }
}
