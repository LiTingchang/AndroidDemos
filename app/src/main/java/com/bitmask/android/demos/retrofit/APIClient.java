package com.bitmask.android.demos.retrofit;

import retrofit2.Retrofit;

public class APIClient {
    public static SpaceXService spaceXService;

    public static SpaceXService getService() {
        if (spaceXService == null) {
            Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
            spaceXService = retrofit.create(SpaceXService.class);
        }
        return spaceXService;
    }
}
