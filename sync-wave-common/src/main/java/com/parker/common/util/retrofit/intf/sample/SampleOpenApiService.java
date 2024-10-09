package com.parker.common.util.retrofit.intf.sample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SampleOpenApiService {
    @GET("api")
    Call<Object> getRandomUserInfo(
            @Query(value = "nat") String nat
    );
}
