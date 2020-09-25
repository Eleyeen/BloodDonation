
package com.example.blooddonation.models.searchBloodGroupModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchBloodGroupRespones {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<BloodGroupSearchDataModel> mData;
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

    public List<BloodGroupSearchDataModel> getData() {
        return mData;
    }

    public void setData(List<BloodGroupSearchDataModel> data) {
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
