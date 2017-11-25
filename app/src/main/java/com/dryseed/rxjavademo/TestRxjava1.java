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
//
///**
// * Created by caiminming on 2017/11/24.
// * <p>
// * Rxjava1.0 基本用法
// */
//
//public class TestRxjava1 {
//    public static void test() {
//        Observer observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.d("MMM", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("MMM", "onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d("MMM", "onNext " + s);
//            }
//        };
//
//        //normal
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("1");
//                subscriber.onNext("2");
//                subscriber.onCompleted();
//            }
//        });
//        observable.subscribe(observer);
//
//        //just
//        Observable.just("3", "4").subscribe(observer);
//
//        //from
//        List<String> list = new ArrayList<>();
//        list.add("5");
//        list.add("6");
//        Observable.from(list).subscribe(observer);
//
//        //from
//        String[] strings = {"7", "8"};
//        Observable.from(strings).subscribe(observer);
//    }
//}
