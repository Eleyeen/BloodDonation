package com.example.blooddonation.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.Adapter.AllDonorAdapter;
import com.example.blooddonation.Adapter.NearByAdapter;
import com.example.blooddonation.Models.NearModel.Datum;
import com.example.blooddonation.Models.NearModel.NearByResponesModel;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByFragment extends Fragment {
    private View view;
    NearByAdapter nearByAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<Datum> allDonor = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.rvNearBy)
    RecyclerView rvNearBy;
    int lastScrollPosition = 0;
    String strCityName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_near_by, container, false);
        ButterKnife.bind(this,view);
        strCityName = GeneralUtills.getSharedPreferences(getActivity()).getString("area", "");

        APiNearBy(strCityName);
        recylcerView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiNearBy(String strCityName) {
        Toast.makeText(getActivity(), "All Donor Login", Toast.LENGTH_SHORT).show();
        Call<NearByResponesModel> getUserResponseModelCall = BaseNetworking.apiServices().GetNearBy(strCityName);
        getUserResponseModelCall.enqueue(new Callback<NearByResponesModel>() {
            @Override
            public void onResponse(Call<NearByResponesModel> call, Response<NearByResponesModel> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    nearByAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearByResponesModel> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

            }
        });
    }

    private void recylcerView() {
        rvNearBy.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvNearBy.setLayoutManager(linearLayoutManager);
        rvNearBy.setItemAnimator(new DefaultItemAnimator());
        rvNearBy.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        nearByAdapter = new NearByAdapter(allDonor, getActivity());
        rvNearBy.setAdapter(nearByAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        allDonor.clear();
        APiNearBy(strCityName);
        linearLayoutManager.onRestoreInstanceState(state);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


}