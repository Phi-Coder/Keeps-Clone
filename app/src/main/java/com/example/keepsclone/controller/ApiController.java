package com.example.keepsclone.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    public static final String BASE_URL = "https://appsbyyuvraj.pythonanywhere.com/keeps/";

    public static Retrofit getJson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
