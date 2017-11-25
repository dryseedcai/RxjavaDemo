//package com.dryseed.rxjavademo.retrofit;
//
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitProvider {
//    private static final String ENDPOINT = "https://api.douban.com/";
//
//    public static Retrofit getRetrofit() {
//        return new Retrofit.Builder()
//                .baseUrl(ENDPOINT)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    public static Retrofit getRetrofitWithRxjava() {
//        return new Retrofit.Builder()
//                .baseUrl(ENDPOINT)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//    }
//}
