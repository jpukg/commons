package com.samenea.commons.component;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:applicationContext-resources.xml",
        "classpath:applicationContext-repository-jpa.xml"})
@TestExecutionListeners({AcceptanceTestExecutionListener.class})
public abstract class AbstractJpaAcceptanceTest extends AbstractJUnit4SpringContextTests {
}
