package com.example.blooddonation.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.Adapter.BloodGroupAdapter;
import com.example.blooddonation.Models.GetBloodGroupModel.Datum;
import com.example.blooddonation.Models.GetBloodGroupModel.GetBloodGroupNameModel;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;

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
    private List<Datum> itemLists;
    int lastScrollPosition = 0;
    private Parcelable state;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blood_group, container, false);
        ButterKnife.bind(this, view);

        RecyclerView();
        APiGetBloodGroup();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetBloodGroup() {

        Call<GetBloodGroupNameModel> getUserResponseModelCall = BaseNetworking.apiServices().getgroups();

        getUserResponseModelCall.enqueue(new Callback<GetBloodGroupNameModel>() {
            @Override
            public void onResponse(Call<GetBloodGroupNameModel> call, Response<GetBloodGroupNameModel> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    itemLists.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetBloodGroupNameModel> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

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
        itemLists.clear();
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}