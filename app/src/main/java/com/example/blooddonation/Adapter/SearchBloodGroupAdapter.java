package com.example.blooddonation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonation.Activities.EditProfileActivity;
import com.example.blooddonation.Models.searchBloodGroupModel.Datum;
import com.example.blooddonation.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchBloodGroupAdapter extends RecyclerView.Adapter<SearchBloodGroupAdapter.ViewHolder> {

    List<Datum> getBloodGroupModels;
    Context context;

    ArrayList<String> stringArrayList = new ArrayList<>();

    public SearchBloodGroupAdapter(List<Datum> bloodGroup, Context context) {
        this.getBloodGroupModels = bloodGroup;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchBloodGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custoom_all_donor, parent, false);


        return new SearchBloodGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBloodGroupAdapter.ViewHolder holder, int position) {
        final Datum groupMOdel = getBloodGroupModels.get(position);

        Glide.with(context).load(groupMOdel.getProfileImage()).into(holder.circleImageView);
        holder.tvName.setText(groupMOdel.getFullname());
        holder.tvWeight.setText(groupMOdel.getWeight() );
        holder.tvBloodGroup.setText("Blood Group : "+groupMOdel.getGroupName() );
        holder.tvAge.setText(groupMOdel.getAge() +" Year");

        holder.cvCustomAllDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EditProfileActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("profile_image", groupMOdel.getProfileImage());
                bundle.putString("fullname", groupMOdel.getFullname());
                bundle.putString("email",groupMOdel.getEmail());
                bundle.putString("phone", String.valueOf(groupMOdel.getPhone()));
                bundle.putString("age", String.valueOf(groupMOdel.getAge()));
                bundle.putString("gender", String.valueOf(groupMOdel.getGender()));
                bundle.putString("weight", String.valueOf(groupMOdel.getWeight()));
                bundle.putString("group_name", String.valueOf(groupMOdel.getGroupName()));
                bundle.putString("area", String.valueOf(groupMOdel.getArea()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getBloodGroupModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAge, tvBloodGroup, tvWeight;
        CircleImageView circleImageView;
        CardView cvCustomAllDonor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.civCustoomProfilePic);

            cvCustomAllDonor = itemView.findViewById(R.id.cvCustomAllDonor);

            tvName = itemView.findViewById(R.id.tvCustomNAme);
            tvAge = itemView.findViewById(R.id.tvAgeProfileCustom);
            tvBloodGroup = itemView.findViewById(R.id.tvBloodGroupProfileCustom);
            tvWeight = itemView.findViewById(R.id.tvWeightProfileCustom);

        }
    }
}

