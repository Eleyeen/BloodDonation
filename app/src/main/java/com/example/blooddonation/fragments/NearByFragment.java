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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.NearByAdapter;
import com.example.blooddonation.models.NearModel.Datum;
import com.example.blooddonation.models.NearModel.NearByResponesModel;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.utils.AlertUtils;
import com.example.blooddonation.utils.GeneralUtills;

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
        strCityName = GeneralUtills.getSharedPreferences(getActivity()).getString("area", "");

        APiNearBy(strCityName);
        recylcerView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiNearBy(String strCityName) {
        dialog.show();
        Call<NearByResponesModel> getUserResponseModelCall = BaseNetworking.apiServices().GetNearBy(strCityName);
        getUserResponseModelCall.enqueue(new Callback<NearByResponesModel>() {
            @Override
            public void onResponse(Call<NearByResponesModel> call, Response<NearByResponesModel> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    nearByAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<NearByResponesModel> call, Throwable t) {
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
        rvNearBy.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        nearByAdapter = new NearByAdapter(allDonor, getActivity());
        rvNearBy.setAdapter(nearByAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
    }


}