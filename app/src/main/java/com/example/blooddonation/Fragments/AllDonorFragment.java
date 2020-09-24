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
import com.example.blooddonation.Models.GetDonor.Datum;
import com.example.blooddonation.Models.GetDonor.GetAllDonorModel;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

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
    public static ArrayList<Datum> allDonor = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.rvAllDonor)
    RecyclerView rvAllDonor;
    int lastScrollPosition = 0;
String strUseId;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_donor, container, false);
        ButterKnife.bind(this, view);

        strUseId = GeneralUtills.getSharedPreferences(getActivity()).getString("user_id","");
        if (GeneralUtills.isLogin(getActivity())){
            APiGetAllDonorLogin(strUseId);
        }else {
            APiGetAllDonor();
        }
        recylcerView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonor() {

        Call<GetAllDonorModel> getUserResponseModelCall = BaseNetworking.apiServices().getalldonors();

        getUserResponseModelCall.enqueue(new Callback<GetAllDonorModel>() {
            @Override
            public void onResponse(Call<GetAllDonorModel> call, Response<GetAllDonorModel> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    allDonorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetAllDonorModel> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonorLogin(String strUserId) {
        Call<GetAllDonorModel> getUserResponseModelCall = BaseNetworking.apiServices().getalldonorLogin(strUserId);

        getUserResponseModelCall.enqueue(new Callback<GetAllDonorModel>() {
            @Override
            public void onResponse(Call<GetAllDonorModel> call, Response<GetAllDonorModel> response) {

                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    allDonorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetAllDonorModel> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        allDonor.clear();
        linearLayoutManager.onRestoreInstanceState(state);
    }


    @Override
    public void onPause() {
        super.onPause();
        state = linearLayoutManager.onSaveInstanceState();
    }

}