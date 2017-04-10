package com.focus.projectx.HttpLoader;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Focus on 10.03.2017.
 */

public class RetroClient {
    private static final String BASE_URL = "http://api.focus.zzz.com.ua/";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Link getApiService(){
        return getRetrofitInstance().create(Link.class);
    }
}
