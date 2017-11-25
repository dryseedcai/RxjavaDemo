//package com.dryseed.rxjavademo;
//
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.functions.Action0;
//import rx.functions.Action1;
//
///**
// * Created by caiminming on 2017/11/24.
// * <p>
// * Rxjava1.0 Action
// */
//
//public class TestRxjava2 {
//    public static void test() {
//        Observable.just("1", "2").subscribe(
//                new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("MMM", "onNext " + s);
//                    }
//                },
//                new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.d("MMM", "onError");
//                    }
//                },
//                new Action0() {
//                    @Override
//                    public void call() {
//                        Log.d("MMM", "onCompleted");
//                    }
//                }
//        );
//    }
//}
