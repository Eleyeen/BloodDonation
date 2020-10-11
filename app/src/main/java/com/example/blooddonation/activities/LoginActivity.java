package com.example.blooddonation.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.R;
import com.example.blooddonation.models.loginModel.LoginRespones;
import com.example.blooddonation.network.APIClient;
import com.example.blooddonation.network.ApiInterface;
import com.example.blooddonation.utils.AlertUtils;
import com.example.blooddonation.utils.Connectivity;
import com.example.blooddonation.utils.GeneralUtills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

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

    @BindView(R.id.tvSkipLogin)
    TextView tvSkipLogin;
    @BindView(R.id.cbRememberMe)
    CheckBox cbRememberMe;
    String strUserEmail, strUserPassword;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        if (GeneralUtills.isLogin(this)) {
            finishAffinity();
            startActivity(new Intent(this, HomeActivity.class));
        }

        iniListener();

    }

    private void iniListener() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvSkipLogin.setOnClickListener(this);
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
            case R.id.tvSkipLogin:
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        return valid;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doLogin() {
        dialog.show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        final Call<LoginRespones> userLogin = services.loginUser(strUserEmail, strUserPassword);

        userLogin.enqueue(new Callback<LoginRespones>() {

            @Override
            public void onResponse(Call<LoginRespones> call, Response<LoginRespones> response) {

                if (response.isSuccessful()) {
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_id", String.valueOf(response.body().getData().getId()));
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_name", response.body().getData().getFullname());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_email", response.body().getData().getEmail());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_BloodGroup", response.body().getData().getBloodGroup());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_Age", response.body().getData().getAge());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_Weight", response.body().getData().getWeight());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_phoneNUmber", response.body().getData().getPhone());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_Location", response.body().getData().getArea());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_gender", response.body().getData().getGender());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_profile_image", response.body().getData().getProfileImage());
                    GeneralUtills.putStringValueInEditor(LoginActivity.this, "user_area", response.body().getData().getArea());


                    GeneralUtills.putBooleanValueInEditor(LoginActivity.this, "isLogin", true);
                    finishAffinity();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("zama error", String.valueOf(jsonObject.getString("message")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginRespones> call, Throwable t) {
                Toast.makeText(LoginActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
                Log.d("zama error", String.valueOf(t));
                dialog.dismiss();
            }
        });

    }

}