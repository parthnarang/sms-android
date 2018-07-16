package com.example.abhish.sms.Fragment1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment {
    private ListView listView;
    private  String[] menuItems = {
            "Interest rate on account XXXXX532640 is changed to 11.550% as on 01/10/17. Please contact Branch Manager for any queries.Download Buddy@ http://goo.gl/qUlXqL\n",
            "Received Rs.100.00 from Mohit (99XXXX9071) in your Paytm Wallet. Wallet txn id: 15337203218. 1Lac Accident Insurance at Rs.20 http://m.p-y.tm/pns T&C\n",
            "Pymt rcvd  NEFT - Your a/c ending XX8219 has been credited for Rs 23643 from SAMSUNG INDIA ELECTRONICS PVT LTD vide UTR ref HSBCN17283484402.\n",
            "Txn of Rs.475 made on Kotak Debit card XX5443 on 14-10-2017 16:46:54 at GOIBIBO60179.Combined balance in A/c XX8219 is Rs 84456.83\n",
            "Txn of Rs.1865 made on Kotak Debit card XX5443 on 18-10-2017 17:36:37 at 470000097087589.Combined balance in A/c XX8219 is Rs 77591.83\n",
            "Txn of Rs.1500 made on Kotak Debit card XX5443 on 21-10-2017 15:26:10 at 470000000218923.Combined balance in A/c XX8219 is Rs 76091.83\n"
    };

    public TransactionsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_new, container, false);

        listView = (ListView) view.findViewById(R.id.list_test3);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

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
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> tempList = new ArrayList<String>();

                for(String temp: menuItems){
                    if(temp.toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,tempList);
                listView.setAdapter(adapter);
                return true;
            }
        });


    }

}
