package com.dryseed.rxjavademo.rxjava2;


import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caiminming on 2017/11/25.
 * <p>
 * Rxjava2.0
 * concat / distinct / filter / buffer / timer / interval / delay
 * doOnNext /
 */

public class Rxjava2Test6 {
    public static void test() {
        // concat
        Observable.concat(Observable.just("1", "2"), Observable.just("3", "4"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "concat : " + s);
                    }
                });

        // distince 去重
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "distinct : " + integer + "\n");
                    }
                });

        // filter 过滤
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "filter : " + integer + "\n");
                    }
                });

        // buffer
        // buffer 操作符接受两个参数，buffer(count,skip)，
        // 作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个 Observable
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .buffer(3, 2) //每3个一组，步长为2
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d("MMM", "buffer onNext");
                        for (Integer i : integers) {
                            Log.d("MMM", "buffer : " + i);
                        }
                    }
                });

        // timer
        // timer 和 interval 均默认在新线程
        Observable
                .timer(3, TimeUnit.SECONDS) //延时3秒执行
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("MMM", "timer : " + aLong);
                    }
                });

        // interval
        Disposable disposable =
                Observable
                        .interval(0, 1000, TimeUnit.MILLISECONDS) //每隔 1s 执行一次任务，立即执行第一次任务
                        .take(5) //执行5次，不设置则执行无限次
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d("MMM", "interval : " + aLong);
                            }
                        });
        // onDestroy : if(disposable != null && !disposable.isDisposed()) disposable.dispose();

        //delay
        Observable
                .just("1", "2")
                .delay(4000, TimeUnit.MILLISECONDS) //延迟4s执行
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "delay : " + s);
                    }
                });
    }

    public static void test2() {
        //doOnNext
        //它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它，无疑我们可以这样实现。
        Observable
                .just("1", "2", "3")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "doOnNext doOnNext : " + s);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "doOnNext onNext : " + s);
                    }
                });

        //skip
        Observable
                .just("1", "2", "3")
                .skip(2)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "skip onNext : " + s);
                    }
                });

        //debounce 去除发送频率过快的项
        //下游在收到要发射消息的指令后，会等待一段时间，如果在这段时间内没有新的消息发射指令，
        // 那么它会发射这条消息，否则它会丢弃掉它，从这个新收到的值开始重新等待设置的时间长度。
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e("MMM", "debounce :" + integer + "\n");
                    }
                });

        //defer

    }
}


















