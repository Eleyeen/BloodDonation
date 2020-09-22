package com.example.blooddonation.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.blooddonation.Models.GetDonor.Datum;
import com.example.blooddonation.Models.GetDonor.GetAllDonorModel;
import com.example.blooddonation.Network.BaseNetworking;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.civEditProfilePic)
    CircleImageView civProfilePic;
    @BindView(R.id.tvPersonName)
    TextView tvPersonName;
    @BindView(R.id.tvEmailProfile)
    TextView tvEmailProfile;
    @BindView(R.id.tvPhoneProfile)
    TextView tvPhoneProfile;
    @BindView(R.id.tvGenderProfile)
    TextView tvGenderProfile;
    @BindView(R.id.tvAgeProfile)
    TextView tvAgeProfile;
    @BindView(R.id.tvWeightProfile)
    TextView tvWeightProfile;

    @BindView(R.id.tvAreaProfile)
    TextView tvArea;
    @BindView(R.id.tvBlodGroupProfile)
    TextView tvBloodGroup;


    @BindView(R.id.btnSendSms)
    Button btnSendSms;
    @BindView(R.id.btnCall)
    Button btnCall;

    String strUserId;
    ArrayList<Datum> itemModels = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        strUserId = GeneralUtills.getSharedPreferences(this).getString("user_id", "");

        iniListener();
        APiGetProfileData(strUserId);
    }
    private void iniListener() {
        ButterKnife.bind(this);
        btnCall.setOnClickListener(this);
        btnSendSms.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void APiGetProfileData(String userId) {


        Call<GetAllDonorModel> getUserResponseModelCall = BaseNetworking.apiServices().getalldonorLogin(userId);

        getUserResponseModelCall.enqueue(new Callback<GetAllDonorModel>() {
            @Override
            public void onResponse(Call<GetAllDonorModel> call, Response<GetAllDonorModel> response) {
                itemModels.addAll(response.body().getData());
                String strEmail = response.body().getData().get(0).getEmail();
                String strFullName = response.body().getData().get(0).getFullname();
                String  strGender= response.body().getData().get(0).getGender();
                String  strAge= response.body().getData().get(0).getAge();
                String  strWight= response.body().getData().get(0).getWeight();
                String  strPhoneNum= response.body().getData().get(0).getPhone();
                String  strArea= response.body().getData().get(0).getArea();
                String strBloodGroup =response.body().getData().get(0).getBloodGroup();
                Glide.with(EditProfileActivity.this).load(response.body().getData().get(0).getProfileImage()).into(civProfilePic);


                tvEmailProfile.setText(strEmail);
                tvAgeProfile.setText(strAge);
                tvPersonName.setText(strFullName);
                tvGenderProfile.setText(strGender);
                tvPhoneProfile.setText(strPhoneNum);
                tvWeightProfile.setText(strWight);
                tvArea.setText(strArea);
                tvBloodGroup.setText(strBloodGroup);

            }

            @Override
            public void onFailure(Call<GetAllDonorModel> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));

            }
        });
    }

    private void sentdSMS(String phone, String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(phone, null, msg, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ErrVar) {
                Toast.makeText(getApplicationContext(), ErrVar.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

        }
    }
}