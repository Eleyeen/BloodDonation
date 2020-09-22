package com.example.blooddonation.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.blooddonation.Activities.LoginActivity;
import com.example.blooddonation.R;

public class SplashFragment extends Fragment {

    public static boolean splashBoolean = true;
    int i = 0;
    View parentView;
    ProgressBar pbSplashScreen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_splash, container, false);
        pbSplashScreen = parentView.findViewById(R.id.pb_splash);

        new CountDownTimer(500, 100) {

            public void onTick(long millisUntilFinished) {


                i = i + 1;
                pbSplashScreen.setProgress(i);

            }

            public void onFinish() {
                startActivity(new Intent(getActivity(), LoginActivity.class));



            }

        }.start();

        return parentView;
    }

}