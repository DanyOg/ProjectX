package com.focus.projectx.HttpLoader;

import com.focus.projectx.model.RegisterData;
import com.focus.projectx.model.RegisterRequestStatus;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Focus on 26.02.2017.
 */

public interface Link {

    @POST("/register.php")
    Call<RegisterRequestStatus> registerNewUser(@Query("name") String userName,
                                                @Query("mail") String mail,
                                                @Query("password") String password,
                                                @Query("description") String description);
}
