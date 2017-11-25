//package com.dryseed.rxjavademo.retrofit;
//
//import com.dryseed.rxjavademo.retrofit.entity.LoginRequest;
//import com.dryseed.rxjavademo.retrofit.entity.LoginResponse;
//import com.dryseed.rxjavademo.retrofit.entity.RegisterRequest;
//import com.dryseed.rxjavademo.retrofit.entity.RegisterResponse;
//import com.dryseed.rxjavademo.retrofit.entity.UserBaseInfoRequest;
//import com.dryseed.rxjavademo.retrofit.entity.UserBaseInfoResponse;
//import com.dryseed.rxjavademo.retrofit.entity.UserExtraInfoRequest;
//import com.dryseed.rxjavademo.retrofit.entity.UserExtraInfoResponse;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Response;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Query;
//
///**
// * Retrofit单独使用，不结合Rxjava
// */
//public interface RetrofitService {
//    @POST("/")
//    Call<LoginResponse> login(@Body LoginRequest request);
//
//    @POST("/")
//    Call<RegisterResponse> register(@Body RegisterRequest request);
//
//    @POST("/")
//    Call<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);
//
//    @POST("/")
//    Call<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);
//
//    @GET("v2/movie/top250")
//    Call<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
//}
