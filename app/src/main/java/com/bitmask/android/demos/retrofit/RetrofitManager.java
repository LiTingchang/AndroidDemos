package com.bitmask.android.demos.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private final String BASE_URL = "https://api.spacexdata.com/v3/";
    private Retrofit mRetrofit;

    private static class Singleton {
        public static RetrofitManager sInstance = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Singleton.sInstance;
    }

    public void initialize() {
        if(mRetrofit == null) {
            //初始化一个OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .readTimeout(15000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
//            builder.addInterceptor(new AuthorizeInterceptor());
            OkHttpClient okHttpClient = builder.build();

            //使用该OkHttpClient创建一个Retrofit对象
            mRetrofit = new Retrofit.Builder()
                    //添加Gson数据格式转换器支持
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加RxJava语言支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //指定网络请求client
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .build();
        }
    }

    public Retrofit getRetrofit() {
        if(mRetrofit == null) {
            throw  new IllegalStateException("Retrofit instance hasn't init!");
        }
        return mRetrofit;
    }

//    public static class AuthorizeInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (UserAccountManager.getInstance().getUser() != null
//                    && !TextUtils.isEmpty(UserAccountManager.getInstance().getUser().AccessToken)) {
//                Request.Builder builder = request.newBuilder();
//                builder.addHeader("HTTP-AUTHORIZATION", UserAccountManager.getInstance().getUser().AccessToken);
//                request = builder.build();
//            }
//            return chain.proceed(request);
//        }
//    }
}
