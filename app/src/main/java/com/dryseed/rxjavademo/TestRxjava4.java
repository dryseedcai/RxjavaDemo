//package com.dryseed.rxjavademo;
//
//import android.util.Log;
//
//import rx.Observable;
//import rx.Scheduler;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//
///**
// * Created by caiminming on 2017/11/24.
// * <p>
// * Rxjava1.0 Action
// */
//
//public class TestRxjava4 {
//    public static void test() {
////        Observable.just("1", "2")
////                .subscribeOn(Schedulers.newThread()) //指定 subscribe() 发生在新的线程
////                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
////                .subscribe(new Action1<String>() {
////                    @Override
////                    public void call(String s) {
////                        //onComplete
////                        Log.d("MMM", Thread.currentThread().getName());
////                    }
////                });
//
//        Observable.just("1", "2")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .map(new Func1<String, Integer>() {
//                    @Override
//                    public Integer call(String s) {
//                        Log.d("MMM", Thread.currentThread().getName() + " " + s);
//                        return s.hashCode();
//
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        Log.d("MMM", Thread.currentThread().getName() + " " + integer);
//                    }
//                });
//
//    }
//}
