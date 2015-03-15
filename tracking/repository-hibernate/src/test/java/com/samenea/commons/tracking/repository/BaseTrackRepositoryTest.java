package com.samenea.commons.tracking.repository;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public abstract class BaseTrackRepositoryTest  extends AbstractTransactionalJUnit4SpringContextTests {
}
