package com.example.blooddonation.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.adapter.SearchBloodGroupAdapter;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.models.GetDonor.AllDonorResponse;
import com.example.blooddonation.models.GetDonor.AllDonorDataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBloodGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<AllDonorDataModel> bloodGroup = new ArrayList<>();
    private Parcelable state;
    SearchBloodGroupAdapter bloodGroupAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rvsearchBlodGroup)
    RecyclerView rvsearchBlodGroup;
    String strBloodGroupId;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_group);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        strBloodGroupId = bundle.getString("id");
        Toast.makeText(this, strBloodGroupId, Toast.LENGTH_LONG).show();
        APiSearchBloodGroup(strBloodGroupId);
        recylcerView();


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiSearchBloodGroup(String strBlodGroupId) {
        Call<AllDonorResponse> getUserResponseModelCall = BaseNetworking.apiServices().GetSearchBloodGroup(strBlodGroupId);
        getUserResponseModelCall.enqueue(new Callback<AllDonorResponse>() {
            @Override
            public void onResponse(Call<AllDonorResponse> call, Response<AllDonorResponse> response) {

                if (response.isSuccessful()) {
                    bloodGroup.addAll(response.body().getData());
                    bloodGroupAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<AllDonorResponse> call, Throwable t) {

            }
        });
    }

    private void recylcerView() {
        rvsearchBlodGroup.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(this);
        rvsearchBlodGroup.setLayoutManager(linearLayoutManager);
        rvsearchBlodGroup.setItemAnimator(new DefaultItemAnimator());
        rvsearchBlodGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        bloodGroupAdapter = new SearchBloodGroupAdapter(bloodGroup, this);
        rvsearchBlodGroup.setAdapter(bloodGroupAdapter);

    }

    @Override
    public void onClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        bloodGroup.clear();
        linearLayoutManager.onRestoreInstanceState(state);
    }


    @Override
    public void onPause() {
        super.onPause();
        state = linearLayoutManager.onSaveInstanceState();
    }

}