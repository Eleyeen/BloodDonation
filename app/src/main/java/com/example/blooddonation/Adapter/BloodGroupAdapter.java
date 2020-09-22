package com.example.blooddonation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.Models.GetBloodGroupModel.Datum;
import com.example.blooddonation.R;

import java.util.ArrayList;
import java.util.List;

public  class BloodGroupAdapter extends RecyclerView.Adapter<BloodGroupAdapter.ViewHolder> {


    List<Datum> getBloodGroup;
    Context context;

    ArrayList<String> stringArrayList = new ArrayList<>();

    public BloodGroupAdapter(List<Datum> getBloodGroups, Context context) {
        this.getBloodGroup = getBloodGroups;
        this.context = context;
    }
    @NonNull
    @Override
    public BloodGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custoom_blood_group, parent, false);


        return new BloodGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodGroupAdapter.ViewHolder holder, int position) {
        final Datum getBloodGroupModel = getBloodGroup.get(position);
        holder.tvBloodGroup.setText(getBloodGroupModel.getName());

    }

    @Override
    public int getItemCount() {
        return getBloodGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBloodGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBloodGroup = itemView.findViewById(R.id.tvBloodGroupCustom);
        }
    }
}
