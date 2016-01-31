package com.health.hhi.asyncjokelibrary;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public static final String URL = "http://10.0.2.2:8080";
    public static final String API = "/_ah/api/myApi/v1/";
    public static final String ENDPOINT = "joke";


    AsyncJoke asyncJoke;
    CountDownLatch signal;
    String foo = "foo";


    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);

    }

    @Override
    protected void tearDown() throws Exception{
        signal.countDown();
    }

    @UiThreadTest
    public void testJoke() throws InterruptedException {

        asyncJoke = new AsyncJoke(new ICallBack() {
            @Override
            public void tellJoke(String joke) {
                assertNotSame(AsyncJoke.JOKE_ERROR, joke);
            }
        });

        asyncJoke.execute(URL + API + ENDPOINT);
        signal.await(15, TimeUnit.SECONDS);

    }

}