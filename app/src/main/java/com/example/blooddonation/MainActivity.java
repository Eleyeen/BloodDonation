package com.example.blooddonation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();

    }

    @Override
    protected void onPause() {
        super.onPause();

        SplashFragment.splashBoolean = false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashFragment.splashBoolean = true;

    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme);
        dialog.setTitle("Spinelli");
        dialog.setMessage("Are you sure you want to Exit?");

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


    }
}