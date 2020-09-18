package com.example.blooddonation.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blooddonation.Models.Forgot.ForgotResponesModel;
import com.example.blooddonation.Network.APIClient;
import com.example.blooddonation.Network.ApiInterface;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity  implements View.OnClickListener {
    @BindView(R.id.etForgot_Email)
    EditText etEmail;

    @BindView(R.id.btnSendCode)
    Button btnSendCode;
    ProgressDialog progressDialog;
    private boolean valid = false;
    private String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initUI();
    }
    private void initUI() {
        ButterKnife.bind(this);
        btnSendCode.setOnClickListener(this);
        progressDialog  = new ProgressDialog(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSendCode:
                if (validate()) {
                    apiCallForgotPassword();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCallForgotPassword() {
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait");
        progressDialog.show();

        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<ForgotResponesModel> userLogin = services.resetPassword(strEmail);
        userLogin.enqueue(new Callback<ForgotResponesModel>() {
            @Override
            public void onResponse(Call<ForgotResponesModel> call, Response<ForgotResponesModel> response) {

                if (response.body() == null) {
                    Toast.makeText(ForgotActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }else if (response.body().getStatus()) {
                    Toast.makeText(ForgotActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(ForgotActivity.this,VerifyActivity.class);
                    intent.putExtra("forgotEmail",strEmail);
                    startActivity(intent);

                } else {
                    Toast.makeText(ForgotActivity.this, "something went wrong please try again with valid email", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotResponesModel> call, Throwable t) {
                Log.d("response","error "+t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    private boolean validate() {
        valid = true;

        strEmail = etEmail.getText().toString();


        GeneralUtills.putStringValueInEditor(ForgotActivity.this,"forgot_email",strEmail);
        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            etEmail.setError("enter a valid email address");
            progressDialog.dismiss();
            valid = false;
        } else {
            etEmail.setError(null);
        }
        return valid;
    }
}
