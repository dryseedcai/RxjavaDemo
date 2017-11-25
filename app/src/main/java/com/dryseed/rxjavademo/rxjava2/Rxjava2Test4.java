package com.dryseed.rxjavademo.rxjava2;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caiminming on 2017/11/25.
 * <p>
 * Rxjava2.0 zip
 */

public class Rxjava2Test4 {
    public static void test() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                Log.d("MMM", "emit 1");
                e.onNext(1);
                Thread.sleep(500);

                Log.d("MMM", "emit 2");
                e.onNext(2);
                Thread.sleep(500);

                Log.d("MMM", "emit 3");
                e.onNext(3);
                Thread.sleep(500);

                Log.d("MMM", "emit 4");
                e.onNext(4);
                Thread.sleep(500);

                Log.d("MMM", "onComplete1");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable observable2 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                Log.d("MMM", "emit A");
                e.onNext("A");
                Thread.sleep(500);

                Log.d("MMM", "emit B");
                e.onNext("B");
                Thread.sleep(500);

                Log.d("MMM", "emit C");
                e.onNext("C");
                Thread.sleep(500);

                Log.d("MMM", "onComplete2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable
                .zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("MMM", "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.d("MMM", "onNext " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MMM", "onError : " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("MMM", "onComplete");
                    }
                });
    }
}


















