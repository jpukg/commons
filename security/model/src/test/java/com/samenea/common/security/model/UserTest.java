package com.samenea.common.security.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/10/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserTest {
    private User user;
    @Before
    public void init(){
        user = new User("test","hello");
    }
    @Test
    public void failedLoginAttemptedTest() throws Exception {
        user.failedLoginAttempted(2,2,TimeUnit.SECONDS);
        Assert.assertTrue(user.isAccountNonLocked());
        user.failedLoginAttempted(2,2,TimeUnit.SECONDS);
        Assert.assertFalse(user.isAccountNonLocked());
        Thread.sleep(3000);
        Assert.assertTrue(user.isAccountNonLocked());
    }
    @Test
    public void successLoginAttemptedTest() throws Exception {
        user.failedLoginAttempted(1,2,TimeUnit.SECONDS);
        Assert.assertFalse(user.isAccountNonLocked());
        user.successLoginAttempted();
        Assert.assertTrue(user.isAccountNonLocked());
    }
}
