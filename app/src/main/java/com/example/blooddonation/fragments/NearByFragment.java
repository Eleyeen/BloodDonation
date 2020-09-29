package com.example.blooddonation.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.DonorAdapter;
import com.example.blooddonation.network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.fragments.home.HomeFragment;
import com.example.blooddonation.models.donorModel.DonorDataModel;
import com.example.blooddonation.models.donorModel.DonorResponse;
import com.example.blooddonation.utils.AlertUtils;
import com.example.blooddonation.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByFragment extends Fragment {
    private View view;
    DonorAdapter donorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<DonorDataModel> allDonor = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.rvNearBy)
    RecyclerView rvNearBy;
    String strCityName;
    Dialog dialog;


    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near_by, container, false);
        ButterKnife.bind(this,view);
        dialog = AlertUtils.createProgressDialog(getActivity());
        strCityName = GeneralUtills.getSharedPreferences(getActivity()).getString("user_area", "");

        APiNearBy(strCityName);
        recylcerView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiNearBy(String strCityName) {
        dialog.show();
        allDonor.clear();
        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().GetNearBy(strCityName);
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    Collections.reverse(allDonor);
                    donorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                }
                if (allDonor.size() == 0) {
                    tvNoDonorFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoDonorFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DonorResponse> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));
                dialog.dismiss();

            }
        });
    }

    private void recylcerView() {
        rvNearBy.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvNearBy.setLayoutManager(linearLayoutManager);
        rvNearBy.setItemAnimator(new DefaultItemAnimator());
        donorAdapter = new DonorAdapter(allDonor, getActivity());
        rvNearBy.setAdapter(donorAdapter);


        HomeFragment.filterSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                donorAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                donorAdapter.getFilter().filter(queryString);
                return false;
            }
        });
    }

}