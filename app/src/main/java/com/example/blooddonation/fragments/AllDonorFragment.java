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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.adapter.AllDonorAdapter;
import com.example.blooddonation.fragments.home.HomeFragment;
import com.example.blooddonation.models.GetDonor.AllDonorDataModel;
import com.example.blooddonation.models.GetDonor.AllDonorResponse;
import com.example.blooddonation.utils.AlertUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDonorFragment extends Fragment {
    private View view;
    AllDonorAdapter allDonorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<AllDonorDataModel> allDonorDataModels = new ArrayList<>();

    @BindView(R.id.rvAllDonor)
    RecyclerView rvAllDonor;
    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_donor, container, false);
        ButterKnife.bind(this, view);
        dialog = AlertUtils.createProgressDialog(getActivity());

        recylcerView();
        APiGetAllDonor();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonor() {
        dialog.show();
        allDonorDataModels.clear();

        Call<AllDonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().getalldonors();
        getUserResponseModelCall.enqueue(new Callback<AllDonorResponse>() {
            @Override
            public void onResponse(Call<AllDonorResponse> call, Response<AllDonorResponse> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonorDataModels.addAll(response.body().getData());
                    Collections.reverse(allDonorDataModels);
                    allDonorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }

                if (allDonorDataModels.size() == 0) {
                    tvNoDonorFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoDonorFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AllDonorResponse> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));
                dialog.show();

            }
        });
    }

    private void recylcerView() {
        rvAllDonor.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvAllDonor.setLayoutManager(linearLayoutManager);
        rvAllDonor.setItemAnimator(new DefaultItemAnimator());
        rvAllDonor.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        allDonorAdapter = new AllDonorAdapter(allDonorDataModels, getActivity());
        rvAllDonor.setAdapter(allDonorAdapter);

        HomeFragment.filterSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                allDonorAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                allDonorAdapter.getFilter().filter(queryString);
                return false;
            }
        });


    }
}