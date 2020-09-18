package com.example.blooddonation.Network;

import com.example.blooddonation.Models.Forgot.ForgotResponesModel;
import com.example.blooddonation.Models.Login.LoginResponesModel;
import com.example.blooddonation.Models.SignUp.SignUpRespones;
import com.example.blooddonation.Models.Verify.VerifyResponseModel;
import com.example.blooddonation.Models.changePassword.ChangePasswordModel;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponesModel> loginUser(
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
            @Part("blood_group") RequestBody userBloodGroup,
            @Part MultipartBody.Part photo,
            @Part("profile_image") RequestBody fileName);


    @FormUrlEncoded
    @POST("reset")
    Call<ForgotResponesModel> resetPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("checkCode")
    Call<VerifyResponseModel> userVerification(
            @Field("code") String code,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("ChangePassword")
    Call<ChangePasswordModel> changePassword(
            @Field("email") String email,
            @Field("newPassword") String code);


}
