package com.samenea.commons.component.config;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ConfigPlaceHolderTest extends ConfigBaseTest {
    @Autowired
    private ConfigPlaceHolder configPlaceHolder;
    @Value("%{config1:updateIntervalTime[0]}")
    String test1;
    @Value("%{config2:updateIntervalTime}")
    String test2;
    @Value("%{config2:hibernate.connection.driver_class[0]}")
    String test3;
    @Value("%{config2:hibernate.connection.driver_class}")
    String test4;

    @Value("%{noGiven.notGiven}")
    String test5;
    @Value("%{noGiven:notGiven[0]}")
    String test6;
    @Value("%{config2:hibernate.connection.driver_class[1]}")
    String test7;
    @Value("%{config1:sendIntervalTime[2]}")
    String test8;
    @Value("%{config1:sendIntervalTime}")
    String test9;
    @Value("%{config1:sendIntervalTime[0]}")
    String test10;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testConfigPlaceHolder() {
        Assert.assertEquals(test1, "40000");
        Assert.assertEquals(test2, "config2:updateIntervalTime");
        Assert.assertEquals(test3, "org.h2.Driver");
        Assert.assertEquals(test4, "org.h2.Driver");
        Assert.assertEquals(test5, "noGiven.notGiven");
        Assert.assertEquals(test6, "noGiven:notGiven[0]");
        Assert.assertEquals(test7, "config2:hibernate.connection.driver_class[1]");
        Assert.assertEquals(test8, "test");
        Assert.assertEquals(test9, "20000,98777,test");
        Assert.assertEquals(test10, "20000");
    }

    @Test
    public void validatePattern() {
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss.fdfdfd"));
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss"));
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss#dfsdfs"));
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss5555[2]"));
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss:fffff[dfd]"));
        Assert.assertFalse(configPlaceHolder.validatePattern("ssss:fffff[333]"));
        Assert.assertFalse(configPlaceHolder.validatePattern(":ffff[]"));
        Assert.assertFalse(configPlaceHolder.validatePattern("dfd:ssss[2]d"));
        Assert.assertFalse(configPlaceHolder.validatePattern("dfd:ssss]"));
        Assert.assertTrue(configPlaceHolder.validatePattern("dfd:ssss[2]"));
        Assert.assertTrue(configPlaceHolder.validatePattern("dfd:ssss"));
        Assert.assertTrue(configPlaceHolder.validatePattern("dfd:ssss.dfsdf.sdf[0]"));
        Assert.assertTrue(configPlaceHolder.validatePattern("dfd:ssss.jhjhjh.oo"));
    }
}
