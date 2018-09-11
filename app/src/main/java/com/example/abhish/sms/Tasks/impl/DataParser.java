package com.example.abhish.sms.Tasks.impl;

/**
 * Created by parth.narang on 1/12/2018.
 */
import android.os.Handler;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


//COMMENT - MAKE A METHOD IN WHICH I CHANGE VALUES FROM ONE CLASS TO ANOTHER(WHEN PREDICTED DIFF FROM ACTUAL) IN HASHMAPS ETC.
public class DataParser{
   // DataIO instance = DataIO.getInstance();
    static private ArrayList<HashMap<String,Integer>> categories = new ArrayList<HashMap<String,Integer>>();
    //static private HashSet<String> wordSet = new HashSet<String>();//To keep track of all unique words
    static private HashMap<String, Integer> wordSet = new HashMap<String, Integer>();//To keep track of all unique words
    //static private HashMap<String, Integer> classProbabilities = new HashMap<String, Integer>();
    static private int NUM_OF_CATEGORIES = 5;

    //Read, Update and Save tcount values
    static HashMap<String, Integer> totalCount = new HashMap<String, Integer>();
    File json = new File("storage/emulated/0/data.json");
    static private Runnable runnable;

    ArrayList<HashMap<String,Integer>> getCategories(){
        return categories;
    }

    HashMap<String,Integer> getWordSet(){
        return wordSet;
    }

    /*HashMap<String,Integer> getClassProbabalities(){
        return classProbabilities;
    }*/

    int getNumOfCategories(){
        return NUM_OF_CATEGORIES;
    }

    HashMap<String,Integer> getTotalCount(){
        return totalCount;
    }

    /*void setCategories(ArrayList<HashMap<String,Integer>>categories){
        this.categories = categories;
    }

    void setWordSet(HashMap<String,Integer>wordSet){
        this.wordSet = wordSet;
    }

    void setClassProbabalities(HashMap<String,Integer>classProbabilities){
        this.classProbabilities = classProbabilities;
    }

    void setTotalCount(HashMap<String,Integer>totalCount){
        this.totalCount = totalCount;
    }*/

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
        Log.d("check_machine","yes");
        //populate hashmapobj;
        String jsonStr = returnJsonString();
        try {
            //COMMENT - READ WORDSET VALUES FROM JSON FILE TOO!!!!!!!!!!!!!!!! #DONE
            //COMMENT - READ CLASS PROBABILITY VALUES FROM JSON FILE TOO!!!!!!!!!!!!!!!! -(CHANGED) NEED TO GET FROM DB
            JSONObject jobject = new JSONObject(jsonStr);
            totalCount = getMap((JSONObject) jobject.get("COUNT"));
            Log.d("parth_sms", totalCount + " ");
            JSONArray feature = (JSONArray) jobject.getJSONArray("FEATURE");
            JSONObject frequency = (JSONObject) jobject.get("FREQUENCY");
            Log.d("parth_sms", "fre: " + frequency);
            wordSet = getMap((JSONObject) jobject.get("WORDSET"));

            for(int i=0; i<NUM_OF_CATEGORIES ; ++i){
                HashMap<String, Integer> temp = getMap((JSONObject) frequency.get(Integer.toString(i)));
                Log.d("Mera-list", "map: " + temp);
                categories.add(temp);
            }
            //COMMENT - READ WORDSET VALUES HERE
            //COMMENT - READ CLASS PROBABILITY VALUES HERE
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


    public HashMap<String, Integer> getCategoryMap(int cat) {
        try{
            return categories.get(cat);
        }
        catch(Exception ex){
            return null;
        }
    }

    public void writeToJson(String msg, int cat) {
        //COMMENT - WRITE WORDSET TO JSON TOO!!!!!!!!!!!!!!!!!!!!!!!!!!
        HashMap<String,Integer> map = getCategoryMap(cat);
        // int count = tcount.get(cat);
        // tcount.put(String.valueOf(cat), ++count);
        String[] msgparts = msg.split(" ");
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if(map.containsKey(word)){
                int count = map.get(word);
                map.put(word, ++count);
            }
            else
                map.put(word,1);
        }

        for(String word:msgparts){
            wordSet.put(word,1);
        }

        int init_val = totalCount.get(Integer.toString(cat));
        totalCount.put(Integer.toString(cat), init_val + msgparts.length);

        String jsonStr = returnJsonString();
        try {
            JSONObject jobject = new JSONObject(jsonStr);

            JSONObject frequency = (JSONObject) jobject.get("FREQUENCY");
            JSONObject category = new JSONObject(map);
            JSONObject tCount = new JSONObject(totalCount);
            JSONObject wSet = new JSONObject(wordSet);

            jobject.put("COUNT",tCount);//COMMENT - CHECK VALIDITY
            jobject.put("WORDSET",wSet);//COMMENT - CHECK VALIDITY
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

    public void changeJSONCategory(String from_cat,String to_cat,String msg){
        int cat = Integer.parseInt(to_cat);
        writeToJson(msg,cat);

        int cat_from = Integer.parseInt(from_cat);
        HashMap<String,Integer> map = getCategoryMap(cat_from);
        // int count = tcount.get(cat);
        // tcount.put(String.valueOf(cat), ++count);
        String[] msgparts = msg.split(" ");
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            if(map.containsKey(word)){
                int count = map.get(word);
                if(count>0){
                    --count;
                }
                map.put(word,count);
            }
        }

        int init_val = totalCount.get(Integer.toString(cat_from));
        totalCount.put(Integer.toString(cat_from), init_val - msgparts.length);

        String jsonStr = returnJsonString();
        try {
            JSONObject jobject = new JSONObject(jsonStr);

            JSONObject frequency = (JSONObject) jobject.get("FREQUENCY");
            JSONObject category = new JSONObject(map);
            JSONObject tCount = new JSONObject(totalCount);

            jobject.put("COUNT",tCount);//COMMENT - CHECK VALIDITY
            frequency.put(String.valueOf(cat_from),category);
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

    public void WriteToFile(final String msg, final int cat) {
        //COMMENT - CHECK IF MEMORY LEAK IS OCCURING!!
        runnable = new Runnable() {
            @Override
            public void run() {
                writeToJson(msg,cat);
            }
        };
       // instance.getHandler().post(runnable);
    }

    public void ChangeCategoryFile(final String from_cat, final String to_cat, final String msg) {
        runnable = new Runnable() {
            @Override
            public void run() {
                changeJSONCategory(from_cat,to_cat,msg);
            }
        };
    }
}