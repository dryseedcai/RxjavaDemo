//package com.dryseed.rxjavademo.retrofit;
//
//import android.util.Log;
//
//import okhttp3.ResponseBody;
//import retrofit2.Response;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.schedulers.Schedulers;
//
///**
// * Created by caiminming on 2017/11/24.
// */
//
//public class TestRetrofitWithRxjava {
//    public static void test() {
//        RetrofitServiceWithRxjava retrofitService = RetrofitProvider.getRetrofitWithRxjava().create(RetrofitServiceWithRxjava.class);
//
//        retrofitService.getTop250(0, 5)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Response<ResponseBody>>() {
//                    @Override
//                    public void call(Response<ResponseBody> responseBodyResponse) {
//                        Log.d("MMM", "onCompleted " + responseBodyResponse.message());
//                    }
//                });
//    }
//}
