package com.dryseed.rxjavademo.rxjava2;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caiminming on 2017/11/25.
 * <p>
 * Rxjava2.0 Backpressure / Flowable
 */

public class Rxjava2Test5 {
    public static void test() {
        //Observalbe -> Flowable
        Flowable<String> upstream = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                Log.d("MMM", "current requested : " + e.requested());
                e.onNext("1");
                Log.d("MMM", "after emit, requested :" + e.requested());
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<String> downstream = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d("MMM", "onSubscribe");
                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(String s) {
                Log.d("MMM", "onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("MMM", "onError " + t.toString());
            }

            @Override
            public void onComplete() {
                Log.d("MMM", "onComplete");
            }
        };

        /*
         * 首先第一个同步的代码, 为什么上游发送第一个事件后下游就抛出了MissingBackpressureException异常,
         * 这是因为下游没有调用request, 上游就认为下游没有处理事件的能力, 而这又是一个同步的订阅, 既然下游处理不了,
         * 那上游不可能一直等待吧, 如果是这样, 万一这两根水管工作在主线程里, 界面不就卡死了吗, 因此只能抛个异常来提醒我们.
         * 那如何解决这种情况呢, 很简单啦, 下游直接调用request(Long.MAX_VALUE)就行了,
         * 或者根据上游发送事件的数量来request就行了, 比如这里request(1)就可以了.
         */
        //upstream.subscribe(downstream);

        /**
         * 为什么上下游没有工作在同一个线程时, 上游却正确的发送了所有的事件呢?
         * 这是因为在Flowable里默认有一个大小为128的水缸, 当上下游工作在不同的线程中时, 上游就会先把事件发送到这个水缸中,
         * 因此, 下游虽然没有调用request, 但是上游在水缸中保存着这些事件, 只有当下游调用request时,才从水缸里取出事件发给下游.
         */
        upstream
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downstream);
    }
}


















