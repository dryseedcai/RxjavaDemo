package com.dryseed.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dryseed.rxjavademo.rxjava2.Rxjava2Test1;
import com.dryseed.rxjavademo.rxjava2.Rxjava2Test2;
import com.dryseed.rxjavademo.rxjava2.Rxjava2Test3;
import com.dryseed.rxjavademo.rxjava2.Rxjava2Test4;
import com.dryseed.rxjavademo.rxjava2.Rxjava2Test5;
import com.dryseed.rxjavademo.rxjava2.Rxjava2Test6;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TestRxjava1.test();
        //TestRxjava2.test();
        //TestRxjava3.test1();
        //TestRxjava3.test2();
        //TestRxjava4.test();
        //TestRetrofit.test();
        //TestRetrofitWithRxjava.test();

        //Rxjava2Test1.test();
        //Rxjava2Test2.test();
        //Rxjava2Test3.test();
        //Rxjava2Test4.test();
        //Rxjava2Test5.test();
        //Rxjava2Test6.test();
        Rxjava2Test6.test2();
    }
}
