
package com.example.blooddonation.Models.Forgot;

import com.google.gson.annotations.SerializedName;

public class ForgotResponesModel {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private String mData;
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

    public String getData() {
        return mData;
    }

    public void setData(String data) {
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
