package com.example.blooddonation.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.blooddonation.Adapter.NearByAdapter;
import com.example.blooddonation.Adapter.SearchBloodGroupAdapter;
import com.example.blooddonation.Models.NearModel.NearByResponesModel;
import com.example.blooddonation.Models.searchBloodGroupModel.Datum;
import com.example.blooddonation.Models.searchBloodGroupModel.SwarchBloodGroupRespones;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBloodGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<Datum> bloodGroup = new ArrayList<>();
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
    private void iniListener() {


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiSearchBloodGroup(String strBlodGroupId) {
        Call<SwarchBloodGroupRespones> getUserResponseModelCall = BaseNetworking.apiServices().GetSearchBloodGroup(strBlodGroupId);
        getUserResponseModelCall.enqueue(new Callback<SwarchBloodGroupRespones>() {
            @Override
            public void onResponse(Call<SwarchBloodGroupRespones> call, Response<SwarchBloodGroupRespones> response) {

                if (response.isSuccessful()) {
                    bloodGroup.addAll(response.body().getData());
                    bloodGroupAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SwarchBloodGroupRespones> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

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