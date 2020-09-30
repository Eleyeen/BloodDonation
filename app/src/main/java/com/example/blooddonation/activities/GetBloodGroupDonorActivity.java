package com.example.blooddonation.activities;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.DonorAdapter;
import com.example.blooddonation.network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.models.donorModel.DonorDataModel;
import com.example.blooddonation.models.donorModel.DonorResponse;
import com.example.blooddonation.utils.AlertUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBloodGroupDonorActivity extends AppCompatActivity implements View.OnClickListener {

    DonorAdapter donorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<DonorDataModel> allDonor = new ArrayList<>();
    @BindView(R.id.rvAllDonor)
    RecyclerView rvAllDonor;
    @BindView(R.id.tvBloodGroup)
    TextView tvBloodGroup;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;
    Dialog dialog;

    @BindView(R.id.searchView)
    SearchView searchView;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_blood_group_donor);
        ButterKnife.bind(this);
        dialog= AlertUtils.createProgressDialog(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            APiGetAllDonorLogin(bundle.getString("bloodgroup_id"));
            tvBloodGroup.setText(bundle.getString("bloodgroup_name"));

        }
        recylcerView();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetAllDonorLogin(String strUserId) {
        allDonor.clear();
        Log.d("zma id",strUserId);
        dialog.show();
        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().GetSearchBloodGroup(strUserId);
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {
                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    donorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    if (allDonor.size() == 0) {
                        tvNoDonorFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoDonorFound.setVisibility(View.GONE);
                    }
                }else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DonorResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });


    }

    private void recylcerView() {
        rvAllDonor.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(this);
        rvAllDonor.setLayoutManager(linearLayoutManager);
        rvAllDonor.setItemAnimator(new DefaultItemAnimator());
        rvAllDonor.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        donorAdapter = new DonorAdapter(allDonor, this);
        rvAllDonor.setAdapter(donorAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @OnClick({R.id.ivBack})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}
