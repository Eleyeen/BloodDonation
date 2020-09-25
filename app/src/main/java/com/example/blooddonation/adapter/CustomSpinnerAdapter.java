package com.example.blooddonation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.blooddonation.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] objects;
    private String firstElement;
    private boolean isFirstTime;

    ArrayList<String> stringArrayList=new ArrayList<>();


    public CustomSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> stringArrayList, String defaultText) {
        super(context, textViewResourceId, stringArrayList);
        this.context = context;
        this.stringArrayList=stringArrayList;
        this.isFirstTime = true;
        setDefaultText(defaultText);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(isFirstTime) {
//           stringArrayList.get(position) = firstElement;
            isFirstTime = false;
        }
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        notifyDataSetChanged();
        return getCustomView(position, convertView, parent);
    }

    public void setDefaultText(String defaultText) {
        this.firstElement = stringArrayList.get(0);
//        objects[0] = defaultText;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_layout, parent, false);
        TextView label = row.findViewById(R.id.tvSpinnerText);
        label.setText(stringArrayList.get(position));

        return row;
    }

}