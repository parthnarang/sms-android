package com.example.abhish.sms.Tasks.impl;

import com.example.abhish.sms.util.PreprocessingUtils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class NaiveBayes {
    DataParser dataParser = new DataParser();

    static ArrayList<HashMap<String,Integer>> categories;
    static HashMap<String, Integer> wordSet;
    static HashMap<String, Double> classProbabilities = new HashMap<String,Double>();
    static int NUM_OF_CATEGORIES;

    //Read, Update and Save tcount values
    static HashMap<String, Integer> totalCount;


    void populateDataStructures(){
        categories = dataParser.getCategories();
        wordSet = dataParser.getWordSet();
        NUM_OF_CATEGORIES = dataParser.getNumOfCategories();
        totalCount = dataParser.getTotalCount();

        //COMMENT: GET VALUES FOR CLASS PROBABILITY FROM DATABASE!!!!!!!!!!!
        //classProbabilities = dataParser.getClassProbabalities();
        //COMMENT - CHANGE BELOW CODE!!!!!!!!! THIS IS DUMMY DATA
        classProbabilities.put("0",0.2);
        classProbabilities.put("1",0.2);
        classProbabilities.put("2",0.2);
        classProbabilities.put("3",0.2);
        classProbabilities.put("4",0.2);

        /*
        if(mode == BULK){
            classProbabilities.put("0",0.2);
            classProbabilities.put("1",0.2);
            classProbabilities.put("2",0.2);
            classProbabilities.put("3",0.2);
            classProbabilities.put("4",0.2);
        }
        else{
            //getclassProbabilities() of all classes
        }

         */
    }


    static int NaiveBayesClassifier(String msg) {
        String[] msgparts = msg.split(" ");
        ArrayList<Double> probabilities = new ArrayList<Double>();

        for(int j=0;j<categories.size();++j) {
            double probability = 1.0;
            for (int i = 0; i < msgparts.length; ++i) {
                String word = msgparts[i];
                if (categories.get(j).containsKey(word)) {

                    probability *= ((double) categories.get(j).get(word)) / (double) (totalCount.get(Integer.toString(j)));
                } else {
                    Log.d("parth_sms", totalCount + " " + totalCount.size() + " " + totalCount.get(Integer.toString(j)) + " ");
                    //probability *= 1.0 / (double) (tcount.get(Integer.toString(i)));
                    probability *= 1.0 / (double) (wordSet.size());
                }
            }
            Log.d("logee",""+j+"\n");
            probability *= classProbabilities.get(Integer.toString(j));
            probabilities.add(probability);
        }

        int category = -1;
        double max_value = -1;
        for(int i=0;i<probabilities.size();++i){
            double value = probabilities.get(i);
            if(value>max_value){
                max_value = value;
                category = i;
            }
        }
        return category;
    }

    public void changeCategory(String from_cat,String to_cat,String msg){
        dataParser.ChangeCategoryFile(from_cat,to_cat,msg);
    }

    @SuppressWarnings("static-access")
    public int getCategory(String txt,String number) {
        // data cleaning
        String msg = PreprocessingUtils.cleanData(txt);

        populateDataStructures();

        int prediction = NaiveBayesClassifier(msg + " " + number);

        dataParser.WriteToFile(msg,prediction);

        return prediction;
    }

}

