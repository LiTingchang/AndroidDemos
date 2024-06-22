package com.bitmask.android.demos.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SpaceXService {
    @GET("history")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<ResponseData> getHistory();
}
