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

import com.example.blooddonation.adapter.AllDonorAdapter;
import com.example.blooddonation.models.GetDonor.DonorDataModel;
import com.example.blooddonation.models.GetDonor.DonorResponse;
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

public class AllDonorFragment extends Fragment {
    private View view;
    AllDonorAdapter allDonorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<DonorDataModel> allDonor = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.rvAllDonor)
    RecyclerView rvAllDonor;
    String strUseId;

    Dialog dialog;
    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_donor, container, false);
        ButterKnife.bind(this, view);
        dialog = AlertUtils.createProgressDialog(getActivity());

        strUseId = GeneralUtills.getSharedPreferences(getActivity()).getString("user_id", "");
        if (GeneralUtills.isLogin(getActivity())) {
            APiGetAllDonorLogin(strUseId);
        } else {
            APiGetAllDonor();
        }
        recylcerView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonor() {
        dialog.show();

        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().getalldonors();
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    allDonorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
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
                dialog.show();

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonorLogin(String strUserId) {
        dialog.show();
        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().getalldonorLogin(strUserId);
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    allDonorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
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
        rvAllDonor.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvAllDonor.setLayoutManager(linearLayoutManager);
        rvAllDonor.setItemAnimator(new DefaultItemAnimator());
        rvAllDonor.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        allDonorAdapter = new AllDonorAdapter(allDonor, getActivity());
        rvAllDonor.setAdapter(allDonorAdapter);

    }
}