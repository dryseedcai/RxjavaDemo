//package com.dryseed.rxjavademo.retrofit;
//
//import android.util.Log;
//
//import com.dryseed.rxjavademo.retrofit.entity.LoginResponse;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by caiminming on 2017/11/24.
// */
//
//public class TestRetrofit {
//    public static void test() {
//        RetrofitService retrofitService = RetrofitProvider.getRetrofit().create(RetrofitService.class);
//        Call<Response<ResponseBody>> call = retrofitService.getTop250(0, 5);
//        call.enqueue(new Callback<Response<ResponseBody>>() {
//            @Override
//            public void onResponse(Call<Response<ResponseBody>> call, Response<Response<ResponseBody>> response) {
//                Log.d("MMM", "onResponse : " + response.message());
//            }
//
//            @Override
//            public void onFailure(Call<Response<ResponseBody>> call, Throwable t) {
//                Log.d("MMM", "onFailure");
//            }
//        });
//    }
//}
