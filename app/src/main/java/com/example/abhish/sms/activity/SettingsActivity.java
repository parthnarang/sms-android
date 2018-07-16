package com.example.abhish.sms.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.abhish.sms.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Spinner spamSpinner;
    private Spinner tempSpinner;
    private Toolbar toolbar;
    private Switch tempSwitch, spamSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        spamSpinner = (Spinner) findViewById(R.id.remove_spam_spinner);
        tempSpinner = (Spinner) findViewById(R.id.remove_spinner);
        tempSwitch = (Switch) findViewById(R.id.switch_temp);
        spamSwitch = (Switch) findViewById(R.id.switch_spam);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Spinner click listener
        //spamSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Never");
        categories.add("After 15 min");
        categories.add("After 30 min");
        categories.add("After 1 hr");
        categories.add("After 1 day");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spamSpinner.setAdapter(dataAdapter);
        tempSpinner.setAdapter(dataAdapter);

        tempSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!(tempSwitch.isChecked())){
                    RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.temp_sms_layout);
                    for ( int i = 0; i < myLayout.getChildCount();  i++ ){
                        View view = myLayout.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                }
                else{
                    RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.temp_sms_layout);
                    for ( int i = 0; i < myLayout.getChildCount();  i++ ){
                        View view = myLayout.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                }
            }
        });

        spamSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!(spamSwitch.isChecked())){
                    RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.spam_sms_layout);
                    for ( int i = 0; i < myLayout.getChildCount();  i++ ){
                        View view = myLayout.getChildAt(i);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                }
                else{
                    RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.spam_sms_layout);
                    for ( int i = 0; i < myLayout.getChildCount();  i++ ){
                        View view = myLayout.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
