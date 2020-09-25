package com.example.blooddonation.adapter;

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
import com.example.blooddonation.activities.EditProfileActivity;
import com.example.blooddonation.models.NearModel.Datum;
import com.example.blooddonation.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.ViewHolder> {

    List<Datum> getNearByModelList;
    Context context;


    public NearByAdapter(List<Datum> getNearModel, Context context) {
        this.getNearByModelList = getNearModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custoom_all_donor, parent, false);


        return new NearByAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Datum getNearByModel = getNearByModelList.get(position);

        Glide.with(context).load(getNearByModel.getProfileImage()).into(holder.circleImageView);
        holder.tvName.setText(getNearByModel.getFullname());
        holder.tvWeight.setText(getNearByModel.getWeight() );
        holder.tvBloodGroup.setText("Blood Group : "+getNearByModel.getBloodGroup() );
        holder.tvAge.setText(getNearByModel.getAge() +" Year");

        holder.cvCustomAllDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("profile_image", getNearByModel.getProfileImage());
                bundle.putString("fullname", getNearByModel.getFullname());
                bundle.putString("email",getNearByModel.getEmail());
                bundle.putString("phone", String.valueOf(getNearByModel.getPhone()));
                bundle.putString("age", String.valueOf(getNearByModel.getAge()));
                bundle.putString("gender", String.valueOf(getNearByModel.getGender()));
                bundle.putString("weight", String.valueOf(getNearByModel.getWeight()));
                bundle.putString("group_name", String.valueOf(getNearByModel.getBloodGroup()));
                bundle.putString("area", String.valueOf(getNearByModel  .getArea()));
                intent.putExtras(bundle);
                context.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return getNearByModelList.size();
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
