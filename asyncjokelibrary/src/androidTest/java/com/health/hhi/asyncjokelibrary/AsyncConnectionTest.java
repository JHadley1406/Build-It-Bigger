package com.health.hhi.asyncjokelibrary;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Josiah Hadley on 1/25/2016.
 */
public class AsyncConnectionTest extends AndroidTestCase {
    public static final String URL = "http://10.0.2.2:8080";
    public static final String API = "/_ah/api/myApi/v1/";
    public static final String ENDPOINT = "joke";


    AsyncJoke asyncJoke;
    CountDownLatch signal;
    String foo = "foo";

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        signal = new CountDownLatch(1);

    }

    @Override
    protected void tearDown() throws Exception{
        signal.countDown();
    }


    public void testJoke() throws InterruptedException{
        asyncJoke = new AsyncJoke(new ICallBack() {
            @Override
            public void tellJoke(String joke) {
                //assertNotSame(AsyncJoke.JOKE_ERROR, joke);
                assertEquals(foo, joke);
                signal.countDown();
            }
        });

        asyncJoke.execute(URL + API + ENDPOINT);
        signal.await();

    }

}
