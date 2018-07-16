package com.example.abhish.sms.Fragment1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.abhish.sms.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrimaryFragment extends Fragment{
    private ListView listView;
    ArrayAdapter<String> listViewAdapter;
    private  String[] menuItems = {
            "Dear Customer,\n" + "We have received SIM card replacement request for your Jio Number 8619396945. Reference number for your request is CO00000NAE7C. Your new Jio SIM will be ready for use in 30 minutes.\n"+ "Thank you, \n" + "Team Jio.\n",
            "Dear Customer,\n" + "To start using Jio data services, please configure APN by going to Settings -> Cellular Networks -> Access Point -> Create NEW APN -> Under APN Type: Jionet -> Save -> Make this the default APN.\n Thank you,\n" + "Team Jio.\n",
            "Your handset has been configured. For laptop surfing use bsnlnet. \nThanks.",
            "Many Many Happy Returns of the day. May the coming year be full of health, happiness and prosperity.\n",
            "Manas- 7065080554\n",
            "First name : 1235  anil\n" + "Mobile : 9560087452\n",
            "Mahalaxmi Travels - Your journey details: BusNo: RJ14PE0108, ServiceNo: Dlh-Jpr,  Track YourBus at https://goo.gl/85hwId\n",
            "Knox Portal Id: Random Id \n Password: RandomPassword"
    };

    public PrimaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primary_new, container, false);

        listView = (ListView) view.findViewById(R.id.list_test);
        listViewAdapter = new ArrayAdapter<String>(
          getActivity(), android.R.layout.simple_list_item_1, menuItems);

        listView.setAdapter(listViewAdapter);

        // check for problems
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = listView.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),""+text,Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.side_nav,menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return ;
    }


}
