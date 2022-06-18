package com.geektech.repeat53.network;

import com.geektech.repeat53.network.model.PixabayModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://pixabay.com/api/?key=27723662-43b1a48fa73b0ddcb14cd5408&q=yellow+flowers&image_type=photo

public interface PixabayApi {

    @GET("api/")
    Call<PixabayModel> getImages(@Query("key") String key,
                                 @Query("q") String query,
                                 @Query("per_page") int per_page,
                                 @Query("page") int page);

}
