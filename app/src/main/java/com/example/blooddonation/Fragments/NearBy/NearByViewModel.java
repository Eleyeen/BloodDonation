package com.example.blooddonation.Fragments.NearBy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearByViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearByViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is NearBy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}