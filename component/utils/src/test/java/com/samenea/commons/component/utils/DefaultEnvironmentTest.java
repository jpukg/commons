package com.samenea.commons.component.utils;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Jalal Ashrafi
 */
public class DefaultEnvironmentTest {
//    @Test(expected = InterruptedException.class)
//    public void sleep_should_throw_Interrupted_Exception_when_called_by_same_lock_in_different_threads() throws InterruptedException {
//        final DefaultEnvironment defaultEnvironment = new DefaultEnvironment();
//        final Object lock = new Object();
//        final long duration = 100L;
//        defaultEnvironment.sleep(duration, lock);
//
//        lockInSeparateThread(defaultEnvironment, lock, duration);
//    }

/*
    private void lockInSeparateThread(final DefaultEnvironment defaultEnvironment, final Object lock, final long duration)  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    defaultEnvironment.sleep(duration, lock);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Should not throw interrupted here!");
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
*/

/*
    @Test
    public void sleep_should_make_a_delay_greater_than_sleep_duration() throws InterruptedException {
        DefaultEnvironment defaultEnvironment = new DefaultEnvironment();
        Object lock = new Object();
        final long beforeSleep = System.currentTimeMillis();
        long duration = 100L;
        defaultEnvironment.sleep(duration,lock);
        final long afterSleep = System.currentTimeMillis();
        long elapsedTime = afterSleep - beforeSleep;
        Assert.assertTrue(String.format("Elapsed time should be greater than duration (%s) but is %s",duration,elapsedTime), elapsedTime >= duration);

    }
*/
}
