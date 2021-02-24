package com.example.keepsclone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface keepsAPI {
    @GET("get")
    Call<keep> getNote();

    @POST("new")
    Call<keep> createNote(@Body keep keep);

    @POST("new")
    Call<keeps_data> createNote(@Body keeps_data keeps_data);

    @DELETE("delete")
    Call<keeps_data> deleteNote();
}
