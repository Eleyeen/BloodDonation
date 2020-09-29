package com.example.blooddonation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.activities.HomeActivity;
import com.example.blooddonation.activities.LoginActivity;
import com.example.blooddonation.utils.GeneralUtills;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GeneralUtills.isLogin(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

                }
                finish();
            }
        }, 1000);

    }


}