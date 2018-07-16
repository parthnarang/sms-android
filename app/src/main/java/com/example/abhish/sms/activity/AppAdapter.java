package com.example.abhish.sms.activity;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abhish.sms.R;

import java.util.List;

/**
 * Created by abhi.sh on 1/12/2018.
 */

public class AppAdapter extends BaseAdapter{

    private Context context;
    private int resource;
    private List<Structuremsg> listMsg;
    public AppAdapter(@NonNull Context context, List<Structuremsg> list) {
        this.context = context;
        listMsg = list;
    }

    @Override
    public int getCount() {
        return listMsg.size();
    }

    @Override
    public Structuremsg getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view, null);
        }

        Log.d("MERA", listMsg.get(position).getSender() + " " + position);

        TextView sender = (TextView) view.findViewById(R.id.sender);
        TextView detail = (TextView) view.findViewById(R.id.detail);

        sender.setText(listMsg.get(position).getSender());
        detail.setText(listMsg.get(position).getDetail());
        return view;
    }
}
