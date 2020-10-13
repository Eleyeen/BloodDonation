package com.example.blooddonation.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.adapter.DonorAdapter;
import com.example.blooddonation.models.donorModel.DonorDataModel;
import com.example.blooddonation.models.donorModel.DonorResponse;
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
    DonorAdapter allDonorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<DonorDataModel> allDonorDataModels = new ArrayList<>();

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
        setHasOptionsMenu(true);

        recylcerView();
        APiGetAllDonor();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonor() {
        dialog.show();
        allDonorDataModels.clear();

        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().getalldonors();
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {

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
            public void onFailure(Call<DonorResponse> call, Throwable t) {
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

        allDonorAdapter = new DonorAdapter(allDonorDataModels, getActivity());
        rvAllDonor.setAdapter(allDonorAdapter);

      /*  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        });*/


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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