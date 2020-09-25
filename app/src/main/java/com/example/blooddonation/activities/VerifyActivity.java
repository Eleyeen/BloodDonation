package com.example.blooddonation.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blooddonation.models.Verify.VerifyPasswordResponse;
import com.example.blooddonation.Network.APIClient;
import com.example.blooddonation.Network.ApiInterface;
import com.example.blooddonation.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.et_code_num1)
    EditText et_num1;
    @BindView(R.id.et_code_num2)
    EditText et_num2;
    @BindView(R.id.et_code_num3)
    EditText et_num3;
    @BindView(R.id.et_code_num4)
    EditText et_num4;
    @BindView(R.id.btn_verify)
    Button btnVerify;
    private String strVerifycode;
    String strVerifyCode;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initUI();
    }
    private void initUI() {
        ButterKnife.bind(this);
        btnVerify.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        et_num1.addTextChangedListener(genraltextWatcher);
        et_num2.addTextChangedListener(genraltextWatcher);
        et_num3.addTextChangedListener(genraltextWatcher);
        et_num4.addTextChangedListener(genraltextWatcher);
        progressDialog  = new ProgressDialog(VerifyActivity.this);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apicallVerification(String strForgotEmail) {
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<VerifyPasswordResponse> userVerify = services.userVerification(strVerifycode,strForgotEmail);
        userVerify.enqueue(new Callback<VerifyPasswordResponse>() {
            @Override
            public void onResponse(Call<VerifyPasswordResponse> call, Response<VerifyPasswordResponse> response) {

                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(VerifyActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } catch (Exception e) {

                    }
                } else if (response.body().getStatus()) {
                    Toast.makeText(VerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyActivity.this,ChangePasswordActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<VerifyPasswordResponse> call, Throwable t) {
//                alertDialog.dismiss();
                progressDialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_verify:
                String num1 = et_num1.getText().toString();
                String num2 = et_num2.getText().toString();
                String num3 = et_num3.getText().toString();
                String num4 = et_num4.getText().toString();

                strVerifycode = num1 + num2 + num3 + num4;

                startActivity(new Intent(VerifyActivity.this,ChangePasswordActivity.class));
                if (strVerifycode.equals("")) {
                    Toast.makeText(VerifyActivity.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Bundle bundle = getIntent().getExtras();
                    if(bundle!=null){
                        apicallVerification(bundle.getString("forgotEmail",""));

                    }

                }
                break;
        }
    }


    private TextWatcher genraltextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void afterTextChanged(Editable editable) {
            onDataInput();


            if (et_num1.length() == 1) {
                et_num2.requestFocus();
            }
            if (et_num2.length() == 1) {
                et_num3.requestFocus();
            }
            if (et_num3.length() == 1) {
                et_num4.requestFocus();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onDataInput() {
        String num1 = et_num1.getText().toString();
        String num2 = et_num2.getText().toString();
        String num3 = et_num3.getText().toString();
        String num4 = et_num4.getText().toString();

        strVerifycode = num1 + num2 + num3 + num4;

//        if (strVerifycode.equals("")) {
//            Toast.makeText(VerifyActivity.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
//        } else {
//            Bundle bundle = getIntent().getExtras();
//            if(bundle!=null){
//                apicallVerification(bundle.getString("forgotEmail",""));
//
//            }
//
//        }
    }

}
