package com.example.blooddonation.network;

import com.example.blooddonation.models.bloodGroupNameModel.GetBloodGroupResponse;
import com.example.blooddonation.models.verify.VerifyPasswordResponse;
import com.example.blooddonation.models.changePassword.ChangePasswordResponse;
import com.example.blooddonation.models.donorModel.DonorResponse;
import com.example.blooddonation.models.forgot.ForgotRespones;
import com.example.blooddonation.models.loginModel.LoginRespones;
import com.example.blooddonation.models.signUpModel.SignUpRespones;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginRespones> loginUser(
            @Field("email") String userLogin,
            @Field("password") String userPassword);


    @Multipart
    @POST("signup")
    Call<SignUpRespones> createUser(
            @Part("fullname") RequestBody name,
            @Part("email") RequestBody Email,
            @Part("password") RequestBody userPassword,
            @Part("phone") RequestBody userPhone,
            @Part("age") RequestBody userAge,
            @Part("weight") RequestBody userWeight,
            @Part("gender") RequestBody userGender,
            @Part("area") RequestBody userArea,
            @Part("group_id") RequestBody userBloodGroup,
            @Part MultipartBody.Part photo,
            @Part("profile_image") RequestBody fileName);


    @Multipart
    @POST("signup")
    Call<SignUpRespones> createUserWithOutImage(
            @Part("fullname") RequestBody name,
            @Part("email") RequestBody Email,
            @Part("password") RequestBody userPassword,
            @Part("phone") RequestBody userPhone,
            @Part("age") RequestBody userAge,
            @Part("weight") RequestBody userWeight,
            @Part("gender") RequestBody userGender,
            @Part("area") RequestBody userArea,
            @Part("group_id") RequestBody userBloodGroup);


    @FormUrlEncoded
    @POST("reset")
    Call<ForgotRespones> resetPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("checkCode")
    Call<VerifyPasswordResponse> userVerification(
            @Field("code") String code,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("ChangePassword")
    Call<ChangePasswordResponse> changePassword(
            @Field("email") String email,
            @Field("newPassword") String code);

    @GET("getalldonors")
    Call<DonorResponse> getalldonors();

    @GET("getgroups")
    Call<GetBloodGroupResponse> getgroups();

    @GET("bloodgroups?")
    Call<DonorResponse> getalldonorLogin(
            @Query("user_id") String userId);

    @GET("getNearBy?")
    Call<DonorResponse> GetNearBy(
            @Query("cityname") String userCity
    );

    @GET("searchdonors?")
    Call<DonorResponse> GetSearchBloodGroup(
            @Query("group_id") String userGroupId);


}
