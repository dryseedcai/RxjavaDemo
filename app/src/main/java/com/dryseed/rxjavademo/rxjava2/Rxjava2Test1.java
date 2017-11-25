package com.dryseed.rxjavademo.rxjava2;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by caiminming on 2017/11/25.
 *
 * Rxjava2.0 基本使用
 */

public class Rxjava2Test1 {
    public static void test() {
        //常规写法
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("MMM", "onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d("MMM", "onNext " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MMM", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("MMM", "onComplete");
            }
        };
        observable.subscribe(observer);

        //链式调用写法
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //consumer、action
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        }).subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "onNext " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("MMM", "onError");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("MMM", "onComplete");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("MMM", "onSubscribe");
                    }
                });
    }
}


















