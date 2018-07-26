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
    static HashSet<String> wordSet;
    static HashMap<String, Integer> classProbabilities;
    static int NUM_OF_CATEGORIES;

    //Read, Update and Save tcount values
    static HashMap<String, Integer> totalCount;


    void populateDataStructures(){
        categories = dataParser.getCategories();
        wordSet = dataParser.getWordSet();
        classProbabilities = dataParser.getClassProbabalities();
        NUM_OF_CATEGORIES = dataParser.getNumOfCategories();
        totalCount = dataParser.getTotalCount();
    }


    static int NaiveBayesClassifier(String msg) {
        String[] msgparts = msg.split(" ");
        ArrayList<Double> probabilities = new ArrayList<Double>();

        for(int j=0;j<categories.size();++j) {
            double probability = 1.0;
            for (int i = 0; i < msgparts.length; ++i) {
                String word = msgparts[i];
                if (categories.get(i).containsKey(word)) {
                    probability *= ((double) categories.get(i).get(word)) / (double) (totalCount.get(Integer.toString(i)));
                } else {
                    Log.d("parth_sms", totalCount + " " + totalCount.size() + " " + totalCount.get(Integer.toString(i)) + " ");
                    //probability *= 1.0 / (double) (tcount.get(Integer.toString(i)));
                    probability *= 1.0 / (double) (wordSet.size());
                }
            }
            probability *= classProbabilities.get(j);
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

    void updateFrequencies(String msg, int category){
        String[] msgparts = msg.split(" ");
        /*COMMENT - UPDATE VALUES IN HANDLER!!!!!!!!!!
          COMMENT - READ VALUES IN HANDLER!!!!!!!!!!!!
        */

        //update values in hashmap to account for the new words
        for (int i = 0; i < msgparts.length; ++i) {
            String word = msgparts[i];
            HashMap<String,Integer> category_map = categories.get(category);
            if (category_map.containsKey(word)) {
                int init_val = category_map.get(word);
                category_map.put(word, init_val + 1);
            } else {
                category_map.put(word, 1);
            }
            categories.set(category,category_map);
        }

        //update class frequencies
        int init_val = totalCount.get(Integer.toString(category));
        totalCount.put(Integer.toString(category), init_val + msgparts.length);

        //add Words to Wordset
        wordSet.addAll(Arrays.asList(msgparts));//COMMENT - VERIFY CORRECTNESS OF THIS LINE!!!!!!!!!!!!!!!!
        //COMMENT - THIS VALUE MUST BE CHANGED IF THE USER CHOOSES TO CHANGE THE PREDICTION, WELL THAT IS A DIFFERENT STORY
        //AND SHALL BE EASY BECAUSE WE SHALL CHANGE THE VALUE OF THE PREDICTED CLASS AND INCREASE THE VALUE OF ACTUAL CLASS
        //THE SAME SHALL APPLY FOR WORD FREQUENCIES HASHSET, THE VALUES SHALL HAVE TO BE MODIFIED IN PREDICTED AND ACTUAL CLASS
        classProbabilities.put(Integer.toString(category),classProbabilities.get(Integer.toString(category) + 1));

        dataParser.setCategories(categories);
        dataParser.setClassProbabalities(classProbabilities);
        dataParser.setTotalCount(totalCount);
        dataParser.setWordSet(wordSet);

    }


    @SuppressWarnings("static-access")
    public int getCategory(String txt) {
        // data cleaning
        String msg = PreprocessingUtils.cleanData(txt);

        populateDataStructures();

        int prediction = NaiveBayesClassifier(msg);

        updateFrequencies(msg,prediction);

        dataParser.WriteToFile(msg,prediction);

        return prediction;
    }

}

