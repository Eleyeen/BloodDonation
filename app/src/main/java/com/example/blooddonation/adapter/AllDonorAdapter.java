package com.example.blooddonation.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonation.activities.EditProfileActivity;
import com.example.blooddonation.R;
import com.example.blooddonation.models.GetDonor.AllDonorDataModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDonorAdapter extends RecyclerView.Adapter<AllDonorAdapter.ViewHolder> {

    List<AllDonorDataModel> getAllDonorModels;
    List<AllDonorDataModel> filterAllDonorModels;
    Context context;

    ArrayList<String> stringArrayList = new ArrayList<>();

    public AllDonorAdapter(List<AllDonorDataModel> allDonorModel, Context context) {
        this.getAllDonorModels = allDonorModel;
        this.filterAllDonorModels=allDonorModel;
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
        final AllDonorDataModel getAllDonorModel = getAllDonorModels.get(position);

        Glide.with(context).load(getAllDonorModel.getProfileImage()).placeholder(R.drawable.profile_image).into(holder.circleImageView);
        holder.tvName.setText(getAllDonorModel.getFullname());
        holder.tvWeight.setText(getAllDonorModel.getWeight());
        holder.tvBloodGroup.setText("Blood Group : " + getAllDonorModel.getGroupName() );
        holder.tvAge.setText(getAllDonorModel.getAge() + " Year");

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getAllDonorModel.getPhone()));
                context.startActivity(intent);
            }
        });


        holder.cvCustomAllDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EditProfileActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("profile_image", getAllDonorModel.getProfileImage());
                bundle.putString("fullname", getAllDonorModel.getFullname());
                bundle.putString("email", getAllDonorModel.getEmail());
                bundle.putString("phone", String.valueOf(getAllDonorModel.getPhone()));
                bundle.putString("age", String.valueOf(getAllDonorModel.getAge()));
                bundle.putString("gender", String.valueOf(getAllDonorModel.getGender()));
                bundle.putString("weight", String.valueOf(getAllDonorModel.getWeight()));
                bundle.putString("group_name", String.valueOf(getAllDonorModel.getGroupName()));
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
        Button btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.civCustoomProfilePic);

            cvCustomAllDonor = itemView.findViewById(R.id.cvCustomAllDonor);

            tvName = itemView.findViewById(R.id.tvCustomNAme);
            tvAge = itemView.findViewById(R.id.tvAgeProfileCustom);
            tvBloodGroup = itemView.findViewById(R.id.tvBloodGroupProfileCustom);
            tvWeight = itemView.findViewById(R.id.tvWeightProfileCustom);
            btnCall = itemView.findViewById(R.id.btnCall);

        }
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    getAllDonorModels = filterAllDonorModels;
                } else {
                    List<AllDonorDataModel> filteredList = new ArrayList<>();
                    for (AllDonorDataModel name : filterAllDonorModels) {
                        if (name.getFullname().toLowerCase().contains(charSequenceString.toLowerCase()) || name.getFullname().contains(charSequenceString)) {
                            filteredList.add(name);
                        }
                        getAllDonorModels = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = getAllDonorModels;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                getAllDonorModels = (List<AllDonorDataModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
