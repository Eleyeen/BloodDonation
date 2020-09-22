
package com.example.blooddonation.Models.GetBloodGroupModel;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
