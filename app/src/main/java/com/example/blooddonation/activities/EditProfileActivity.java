package com.example.blooddonation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.blooddonation.models.GetDonor.DonorDataModel;
import com.example.blooddonation.R;
import com.example.blooddonation.utils.GeneralUtills;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PHONE_CALL = 1;
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
    String strPhone;

    @BindView(R.id.btnSendSms)
    Button btnSendSms;
    @BindView(R.id.btnCall)
    Button btnCall;

    String strUserId;
    ArrayList<DonorDataModel> itemModels = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        strUserId = GeneralUtills.getSharedPreferences(this).getString("user_id", "");

        iniListener();
//        APiGetProfileData(strUserId);
    }

    private void iniListener() {
        ButterKnife.bind(this);
        btnCall.setOnClickListener(this);
        btnSendSms.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            Glide.with(EditProfileActivity.this).load(bundle.getString("profile_image")).into(civProfilePic);

            tvEmailProfile.setText(bundle.getString("email"));
            tvAgeProfile.setText(bundle.getString("age"));
            tvPersonName.setText(bundle.getString("fullname"));
            tvGenderProfile.setText(bundle.getString("gender"));
            tvPhoneProfile.setText(bundle.getString("phone"));
            tvWeightProfile.setText(bundle.getString("weight"));
            tvArea.setText(bundle.getString("area"));
            tvBloodGroup.setText(bundle.getString("group_name"));


            strPhone =bundle.getString("phone") ;



        }
    }



    private void SentdSMS(String phone, String msg) {

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

        switch (view.getId()) {

            case R.id.btnCall :

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + strPhone));
                startActivity(intent);


                break;
            case R.id.btnSendSms:

                String strMsg ="Blood Donation \n ";
                SentdSMS(strPhone,strMsg);
                break;

        }
    }
}