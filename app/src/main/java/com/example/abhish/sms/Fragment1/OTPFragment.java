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
public class OTPFragment extends Fragment {
    private ListView listView;
    private  String[] menuItems = {
            "121330 is your Amazon login OTP. OTP is confidential. For security reasons, Do not share this OTP with anyone.\n",
            "G-358735 is your Google verification code.\n",
            "6733bz2e is your code to reset your Twitter password. Don't reply to this message with your code",
            "G-212865 is your Google verification code.\n",
            "77804 is your one time password to proceed on PhonePe. It is valid for 10 minutes.Do not share your OTP with anyone\n ",
            "433679 is the One Time Password (OTP) for your Kotak Debit Card xx5443  valid till 21/10/2017 15:40:39 IST. Do not share your OTP with anyone, bank never calls to verify OTP\n"
    };

    public OTPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ot_new, container, false);

        listView = (ListView) view.findViewById(R.id.list_test2);
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
