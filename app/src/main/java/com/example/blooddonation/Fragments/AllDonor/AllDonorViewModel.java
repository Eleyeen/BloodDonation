package com.example.blooddonation.Fragments.AllDonor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllDonorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllDonorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AllDonor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
