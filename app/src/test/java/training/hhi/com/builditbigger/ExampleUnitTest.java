package training.hhi.com.builditbigger;

import org.junit.Test;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    // generated from sample code https://gist.github.com/he9lin/2195897
    @Test
    public static void jokeTest() throws Throwable{
        final CountDownLatch signal = new CountDownLatch(1);

        runTestOnUIThread(new Runnable()){

            @Override
            public void run(){

            }
        }

    }
}