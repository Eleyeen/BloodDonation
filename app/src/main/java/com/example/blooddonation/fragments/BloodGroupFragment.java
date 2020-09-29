package com.example.blooddonation.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.BloodGroupAdapter;
import com.example.blooddonation.models.bloodGroupNameModel.GetBloodGroupDataModel;
import com.example.blooddonation.models.bloodGroupNameModel.GetBloodGroupResponse;
import com.example.blooddonation.network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.utils.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodGroupFragment extends Fragment {
    @BindView(R.id.rvBloodGroup)
    RecyclerView rvBloodGroup;
    private View view;
    LinearLayoutManager linearLayoutManager;
    BloodGroupAdapter adapter;
    private List<GetBloodGroupDataModel> itemLists;
    Dialog dialog;

    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blood_group, container, false);
        ButterKnife.bind(this, view);
        dialog = AlertUtils.createProgressDialog(getActivity());

        RecyclerView();
        APiGetBloodGroup();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetBloodGroup() {
        dialog.show();

        Call<GetBloodGroupResponse> getUserResponseModelCall = BaseNetworking.apiServices().getgroups();

        getUserResponseModelCall.enqueue(new Callback<GetBloodGroupResponse>() {
            @Override
            public void onResponse(Call<GetBloodGroupResponse> call, Response<GetBloodGroupResponse> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    itemLists.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }

                if (itemLists.size() == 0) {
                    tvNoDonorFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoDonorFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetBloodGroupResponse> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));
                dialog.dismiss();

            }
        });
    }

    private void RecyclerView() {

        rvBloodGroup.setHasFixedSize(true);
        rvBloodGroup.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvBloodGroup.setHasFixedSize(true);
        itemLists = new ArrayList<>();
        adapter = new BloodGroupAdapter(itemLists, getActivity());
        rvBloodGroup.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
    }


}