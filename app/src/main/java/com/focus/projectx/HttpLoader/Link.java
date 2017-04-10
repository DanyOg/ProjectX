package com.focus.projectx.HttpLoader;

import com.focus.projectx.model.AllUserModel;
import com.focus.projectx.model.RegisterData;
import com.focus.projectx.model.RegisterRequestStatus;
import com.focus.projectx.model.UserModel;

import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Focus on 10.03.2017.
 */

public interface Link {
    @Multipart
    @POST("method/users/register")
    Call<UserModel> register(@Part("email")       String email,
                             @Part("password")    String password,
                             @Part("name")        String name,
                             @Part("login")       String login,
                             @Part("surname")     String surname,
                             @Part("description") String description,
                             @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("method/users/login")
    Call<UserModel> login(@Field("email") String email,
                          @Field("password") String password);

    @GET("method/users")
    Call<AllUserModel> getAllUser(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @GET("method/user/follow")
    Call<String> follow(@Field("user_ID") Integer userID);

    @FormUrlEncoded
    @GET("method/user/unfollow")
    Call<String> unfollow(@Field("user_ID") Integer userID);
}
