package com.pedrocarrillo.redditclient;

import android.util.Log;

import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void syntax_RxJava() throws Exception {

        Observable.just("Hello world1")
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
            });

        Observable.from(Arrays.asList(1,2,3))
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer+" ");
                    }
                });

        System.out.println(" ---------------- ");

        Observable.create(observer -> {});

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 0; i < 4; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }

                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        })
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("finished");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);

            }
        });

        assertEquals(4, 2 + 2);
    }

    @Test
    public void maptOperators_RxJava() throws Exception {
        Observable
                .from(Arrays.asList(1,2,3,4))
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer * 2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        System.out.println(o);
                    }
                });

        System.out.println(" ---------------- ");

    }

    @Test
    public void filtertOperator_RxJava() throws Exception {
        Observable
                .from(Arrays.asList(1,2,3,4,5,6))
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        System.out.println(o);
                    }
                });

        System.out.println(" ---------------- ");


    }

    @Test
    public void mergeOperator_RxJava() throws Exception {

        String[] high = new String[]{"a", "b"};
        Observable highSchool = Observable.from(high);

        Observable college = Observable.from(new String[]{"c", "d"});

        Observable.merge(highSchool, college)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println(o + Thread.currentThread().toString());
                    }
                });

    }




}