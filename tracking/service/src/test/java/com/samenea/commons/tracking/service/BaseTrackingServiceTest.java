package com.samenea.commons.tracking.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/12/13
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = {
        "classpath:/context.xml"
})
public abstract class BaseTrackingServiceTest extends AbstractJUnit4SpringContextTests {


}
