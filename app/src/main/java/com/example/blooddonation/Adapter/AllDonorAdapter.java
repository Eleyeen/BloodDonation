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
import com.example.blooddonation.Models.GetDonor.Datum;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDonorAdapter extends RecyclerView.Adapter<AllDonorAdapter.ViewHolder> {

    List<Datum> getAllDonorModels;
    Context context;

    ArrayList<String> stringArrayList = new ArrayList<>();

    public AllDonorAdapter(List<Datum> allDonorModel, Context context) {
        this.getAllDonorModels = allDonorModel;
        this.context = context;
    }

    @NonNull
    @Override
    public AllDonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custoom_all_donor, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDonorAdapter.ViewHolder holder, int position) {
        final Datum getAllDonorModel = getAllDonorModels.get(position);

        Glide.with(context).load(getAllDonorModel.getProfileImage()).into(holder.circleImageView);
        holder.tvName.setText(getAllDonorModel.getFullname());
        holder.tvWeight.setText(getAllDonorModel.getWeight() );
        holder.tvBloodGroup.setText("Blood Group : "+getAllDonorModel.getBloodGroup() );
        holder.tvAge.setText(getAllDonorModel.getAge() +" Year");

        holder.cvCustomAllDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EditProfileActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("profile_image", getAllDonorModel.getProfileImage());
                bundle.putString("fullname", getAllDonorModel.getFullname());
                bundle.putString("email",getAllDonorModel.getEmail());
                bundle.putString("phone", String.valueOf(getAllDonorModel.getPhone()));
                bundle.putString("age", String.valueOf(getAllDonorModel.getAge()));
                bundle.putString("gender", String.valueOf(getAllDonorModel.getGender()));
                bundle.putString("weight", String.valueOf(getAllDonorModel.getWeight()));
                bundle.putString("group_name", String.valueOf(getAllDonorModel.getBloodGroup()));
                bundle.putString("area", String.valueOf(getAllDonorModel.getArea()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllDonorModels.size();
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
