package com.dryseed.rxjavademo.rxjava2;


import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by caiminming on 2017/11/25.
 * <p>
 * Rxjava2.0
 * concat / distinct / filter / buffer / timer / interval / delay
 * doOnNext / defer / last / merge / reduce / window
 * repeatWhen / retryWhen / combineLatest
 * <p>
 * 参考：http://www.jianshu.com/p/2ddd9bb8b1d7
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
                .delay(2000, TimeUnit.MILLISECONDS) //延迟4s执行
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "delay : " + s);
                    }
                });
//        11-28 16:24:39.320 26941-26976/com.dryseed.rxjavademo D/MMM: delay : 1
//        11-28 16:24:39.320 26941-26976/com.dryseed.rxjavademo D/MMM: delay : 2
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
                        Log.d("MMM", "debounce : " + integer);
                    }
                });

        //defer
        //每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just("1", "2", "3");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("MMM", "defer : " + s);
            }
        });

        //last
        //last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
        Observable.just(1, 2, 3, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer i) throws Exception {
                        return i < 3;
                    }
                })
                .last(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
                        Log.d("MMM", "last : " + i);
                    }
                });

        //merge
        //merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
        // 注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
        Observable.merge(Observable.just("1", "2"), Observable.just("3", "4"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("MMM", "merge : " + s);
                    }
                });

        //reduce
        //reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
        //可以看到，代码中，我们中间采用 reduce ，支持一个 function 为两数值相加，所以应该最后的值是：10
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "reduce : " + integer);
                    }
                });

        //scan
        //scan 操作符作用和上面的 reduce 一致，
        // 唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
        Observable.just(1, 2, 3, 4)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("MMM", "scan : " + integer);
                    }
                });

        //window
        //按照实际划分窗口，将数据发送给不同的 Observable
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(15)
                .window(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        Log.d("MMM", "window : longObservable onNext");
                        longObservable.subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d("MMM", "window : " + aLong);
                            }
                        });
                    }
                });
    }

    public static void test3() {
        //repeatWhen
        // 第一次照常执行先出法onNext -> onComplete，之后触发repeatWhen，轮询两次
        // retryWhen和repeatWhen最大的不同就是：retryWhen是收到onError后触发是否要重订阅的询问，而repeatWhen是通过onComplete触发。
        Observable
                .just("99")
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    private long mRepeatCount = 0;

                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        //必须作出反应，这里是通过flatMap操作符。
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                if (++mRepeatCount > 2) {
                                    return Observable.error(new Throwable("work finished!"));
                                }
                                //return Observable.timer(3000 + mRepeatCount * 1000, TimeUnit.MILLISECONDS);
                                return Observable.timer(2000, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d("MMM", "repeatWhen onNext : " + s);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("MMM", "repeatWhen onError : " + throwable.toString());
                            }
                        }
                );

        //retryWhen
        //retryWhen是收到onError后触发是否要重订阅的询问
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    private int mRetryCount = 0;

                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        //模拟一共请求四次，最后一次成功
                        Log.d("MMM", "retryWhen doWork : " + mRetryCount);
                        if (++mRetryCount > 3) {
                            e.onNext("Success");
                            e.onComplete();
                        } else {
                            e.onError(new Throwable("Error"));
                        }
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                if (throwable.getMessage().equals("Error")) {
                                    return Observable.timer(3000, TimeUnit.MILLISECONDS);
                                } else {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                    }
                })
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d("MMM", "retryWhen onNext : " + s);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("MMM", "retryWhen onError : " + throwable.toString());
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d("MMM", "retryWhen onComplete");
                            }
                        }
                );

        //combineLatest
        //combineLatest 操作符用来将多个Observable发射的数据组装起来然后在发射. 通过Func类来组装多个Observable发射的数据,
        //等到最后一个Observable发射数据了, 然后把所有发射的数据交给Fun进行组合, 然后把组合后的数据发射出去
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                int count = 0;
                for (int i = 0; i < 5; i++) {
                    e.onNext(++count);
                    Thread.sleep(1000);
                }
            }
        });
        Observable observable2 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                int count = 0;
                for (int i = 0; i < 5; i++) {
                    e.onNext(++count);
                    Thread.sleep(1000);
                }
            }
        });
        Observable.combineLatest(
                observable1,
                observable2,
                new BiFunction<Integer, Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer, Integer integer2) throws Exception {
                        Log.d("MMM", "combineLatest apply : " + integer + " " + integer2);
                        return integer >= 2 && integer2 >= 2;
                    }
                })
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("MMM", "combineLatest onNext boolean : " + o);
                    }
                });

//        11-28 16:46:21.748 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest apply : 5 1
//        11-28 16:46:21.749 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest onNext boolean : false
//        11-28 16:46:22.749 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest apply : 5 2
//        11-28 16:46:22.749 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest onNext boolean : true
//        11-28 16:46:23.750 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest apply : 5 3
//        11-28 16:46:23.750 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest onNext boolean : true
//        11-28 16:46:24.750 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest apply : 5 4
//        11-28 16:46:24.750 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest onNext boolean : true
//        11-28 16:46:25.751 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest apply : 5 5
//        11-28 16:46:25.751 7911-7911/com.dryseed.rxjavademo D/MMM: combineLatest onNext boolean : true

    }
}


















