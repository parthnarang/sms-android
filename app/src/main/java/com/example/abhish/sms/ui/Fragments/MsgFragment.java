package com.example.abhish.sms.ui.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.abhish.sms.R;
import com.example.abhish.sms.ui.Adapters.AppAdapter;
import com.example.abhish.sms.util.StructureMsg;

import java.util.List;

public class MsgFragment extends Fragment {

    private List<StructureMsg> listMsg;
    private AppAdapter appAdapter;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_msg, container, false);
        listView = (ListView) view.findViewById(R.id.all_messages);
        appAdapter = new AppAdapter(view.getContext(), listMsg);
        listView.setAdapter(appAdapter);
        Log.d("MERA", listMsg.toString());
        return view;

    }

    public void setListMsg(List<StructureMsg> listMsg) {
        this.listMsg = listMsg;
    }
}
