package com.samenea.commons.idgenerator.repository;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {
        "classpath:application-context.xml"
})
@TestExecutionListeners({HiLoGeneratorRepositoryTestExecutionListener.class})
public abstract class AbstractHiLoGeneratorRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
}
