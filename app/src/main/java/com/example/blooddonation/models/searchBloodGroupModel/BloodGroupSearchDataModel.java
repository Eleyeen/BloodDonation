
package com.example.blooddonation.models.searchBloodGroupModel;

import com.google.gson.annotations.SerializedName;

public class BloodGroupSearchDataModel {

    @SerializedName("age")
    private String mAge;
    @SerializedName("area")
    private String mArea;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fullname")
    private String mFullname;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("group_name")
    private String mGroupName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("profile_image")
    private String mProfileImage;
    @SerializedName("weight")
    private String mWeight;

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String area) {
        mArea = area;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String fullname) {
        mFullname = fullname;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        mProfileImage = profileImage;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

}
