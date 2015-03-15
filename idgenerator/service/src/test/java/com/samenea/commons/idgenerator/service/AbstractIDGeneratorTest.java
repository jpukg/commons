package com.samenea.commons.idgenerator.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {
        "classpath:application-context.xml"
})
@TestExecutionListeners({IDGeneratorTestExecutionListener.class})
public abstract class AbstractIDGeneratorTest extends AbstractTransactionalJUnit4SpringContextTests {
}
