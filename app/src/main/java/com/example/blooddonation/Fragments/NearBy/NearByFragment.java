package com.example.blooddonation.Fragments.NearBy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blooddonation.R;

public class NearByFragment extends Fragment {

    private NearByViewModel nearByViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nearByViewModel = ViewModelProviders.of(this).get(NearByViewModel.class);
        View root = inflater.inflate(R.layout.fragment_near_by2, container, false);
        final TextView textView = root.findViewById(R.id.text_NearBy);
        nearByViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}