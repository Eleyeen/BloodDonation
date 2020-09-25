package com.example.blooddonation.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.AllDonorAdapter;
import com.example.blooddonation.models.GetDonor.DonorDataModel;
import com.example.blooddonation.models.GetDonor.DonorResponse;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBloodGroupDonorActivity extends AppCompatActivity implements View.OnClickListener {

    AllDonorAdapter allDonorAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<DonorDataModel> allDonor = new ArrayList<>();
    private Parcelable state;

    @BindView(R.id.rvAllDonor)
    RecyclerView rvAllDonor;
    String strUseId;

    @BindView(R.id.tvBloodGroup)
    TextView tvBloodGroup;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tvNoDonorFound)
    TextView tvNoDonorFound;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_blood_group_donor);
        ButterKnife.bind(this);


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
        Call<DonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().GetSearchBloodGroup(strUserId);
        getUserResponseModelCall.enqueue(new Callback<DonorResponse>() {
            @Override
            public void onResponse(Call<DonorResponse> call, Response<DonorResponse> response) {
                Log.d("zma response", String.valueOf(response.message()));
                if (response.isSuccessful()) {
                    allDonor.addAll(response.body().getData());
                    allDonorAdapter.notifyDataSetChanged();

                    if (allDonor.size() == 0) {
                        tvNoDonorFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoDonorFound.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DonorResponse> call, Throwable t) {

            }
        });


    }

    private void recylcerView() {
        rvAllDonor.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(this);
        rvAllDonor.setLayoutManager(linearLayoutManager);
        rvAllDonor.setItemAnimator(new DefaultItemAnimator());
        rvAllDonor.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        allDonorAdapter = new AllDonorAdapter(allDonor, this);
        rvAllDonor.setAdapter(allDonorAdapter);
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
