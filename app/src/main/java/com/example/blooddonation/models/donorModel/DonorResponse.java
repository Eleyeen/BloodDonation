
package com.example.blooddonation.models.donorModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonorResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<DonorDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public List<DonorDataModel> getData() {
        return mData;
    }

    public void setData(List<DonorDataModel> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
