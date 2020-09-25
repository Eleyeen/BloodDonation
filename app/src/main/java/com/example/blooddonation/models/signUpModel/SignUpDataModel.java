
package com.example.blooddonation.models.signUpModel;


import com.google.gson.annotations.SerializedName;

public class SignUpDataModel {

    @SerializedName("age")
    private String mAge;
    @SerializedName("area")
    private String mArea;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fullname")
    private String mFullname;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("group_id")
    private String mGroupId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("profile_image")
    private String mProfileImage;
    @SerializedName("updated_at")
    private String mUpdatedAt;
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

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
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

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

}
