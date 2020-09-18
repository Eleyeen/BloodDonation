package com.example.blooddonation.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.Fragments.Home.HomeFragment;
import com.example.blooddonation.Models.Login.LoginResponesModel;
import com.example.blooddonation.Network.APIClient;
import com.example.blooddonation.Network.ApiInterface;
import com.example.blooddonation.R;
import com.example.blooddonation.Utails.GeneralUtills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.etEmailLogin)
    EditText etEmail;
    @BindView(R.id.etPasswordLogin)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnSignUp)
    TextView tvSignUp;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgotPassword;
    @BindView(R.id.cbRememberMe)
    CheckBox cbRememberMe;
    String strUserEmail, strUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (GeneralUtills.isLogin(this)) {
            finishAffinity();
            startActivity(new Intent(this, HomePageActivity.class));
        }

        iniListener();

    }

    private void iniListener() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                if (inValid()) {
                    doLogin();
                }
                break;

            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.tvForgetPassword:
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                break;
        }
    }

    private boolean inValid() {
        boolean valid = true;
        strUserEmail = etEmail.getText().toString();
        strUserPassword = etPassword.getText().toString();


        if (strUserEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(strUserEmail).matches()) {
            etEmail.setError("Enter a Valid Email Address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (strUserPassword.isEmpty() || strUserPassword.length() < 6) {
            etPassword.setError("Password should be more than 6 characters");

            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doLogin() {
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        final Call<LoginResponesModel> userLogin = services.loginUser(strUserEmail, strUserPassword);

        userLogin.enqueue(new Callback<LoginResponesModel>() {

            @Override
            public void onResponse(Call<LoginResponesModel> call, Response<LoginResponesModel> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                    GeneralUtills.putBooleanValueInEditor(LoginActivity.this, "isLogin", true);
                    finishAffinity();
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponesModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, " Success Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}