package com.example.blooddonation.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blooddonation.Models.changePassword.ChangePasswordModel;
import com.example.blooddonation.Network.APIClient;
import com.example.blooddonation.Network.ApiInterface;
import com.example.blooddonation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

        @BindView(R.id.etEmailChangePass)
        EditText etEmail;
        @BindView(R.id.etNewPasswordChange)
        EditText etPassword;

        @BindView(R.id.btnChangePassword)
        Button btnChangePassword;

        ProgressDialog progressDialog;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
  initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        btnChangePassword.setOnClickListener(this);
        progressDialog  = new ProgressDialog(ChangePasswordActivity.this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChangePassword:
                ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();

                String userEmail = etEmail.getText().toString();
                String userPassword = etPassword.getText().toString();

                if (validateLogin(userEmail, userPassword)) {
                    apiCallChangePassword(userEmail, userPassword);
                }

                break;
        }
    }

    private boolean validateLogin(String userEmail, String userPassword) {

        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "User Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userPassword.isEmpty() || userPassword.length() < 4 || userPassword.length() > 10) {
            Toast.makeText(this, "User Password is Required", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCallChangePassword(String strEmail,String strNewPassword) {
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<ChangePasswordModel> userLogin = services.changePassword(strEmail,strNewPassword);
        userLogin.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                if (response.body() == null) {
                    Toast.makeText(ChangePasswordActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (response.body().getStatus()) {
                    Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
