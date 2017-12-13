package com.dryseed.rxjavademo.rxjava2;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by caiminming on 2017/11/25.
 * <p>
 * Rxjava2.0 map / flatMap / concatMap / switchMap
 * <p>
 * switchMap :
 * switchMap的原理是将上游的事件转换成一个或多个新的Observable，但是有一点很重要，
 * 就是如果在该节点收到一个新的事件之后，那么如果之前收到的时间所产生的Observable还没有发送事件给下游，那么下游就再也不会收到它发送的事件了。
 */

public class Rxjava2Test3 {
    public static void test() {
        Observable.just("1")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "hashcode : " + integer);
                    }
                });

        Observable.just("1", "2", "3")
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    //.concatMap(new Function<String, ObservableSource<Integer>>() { //concatMap可以保证有序
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(i);
                        }
                        return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "onNext : " + integer);
                    }
                });
    }
}


















