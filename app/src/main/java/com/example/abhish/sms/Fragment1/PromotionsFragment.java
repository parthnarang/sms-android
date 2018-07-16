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
public class PromotionsFragment extends Fragment {
    private ListView listView;
    private  String[] menuItems = {
            "It's time for another train journey! Book your E-tickets on Paytm & enjoy 100% Cashback on payment gateway fee. Use code: TZERO. http://m.p-y.tm/itck\n",
            "Open an account now with KOTAK SECURITIES ALL-IN-1 DEMAT+TRADING+SAVINGS A/C For Fast & Easy Trading. To know more, SMS KS to 5616198 T&CA\n",
            "Checkout best offers near you on Big Bazaar, Shoppers Stop, KFC, Domino's and more http://m.p-y.tm/v9fty Click here to redeem your deals m.p-y.tm/myorders\n",
            "Win Rs 2,50,000 Paytm cash within 5 days! Powerplay Quiz S40 has just gone live. Play now to win Paytm cash. Click on https://goo.gl/DxB4cV now\n",
            "redBus Taking India Home Festival is ON!\n" + "Save upto Rs. 400 on bus tickets. Code : GOHOME | Pay using AmazonPay. Limited time offer. Book - bit.ly/gohomewithrb\n ",
            "Participate in Contest and win upto Rs. 500 Paytm cash instantly today on Flikk. Install Now http://bit.ly/goflikk\n"
    };

    public PromotionsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotions_new, container, false);

        listView = (ListView) view.findViewById(R.id.list_test4);
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
