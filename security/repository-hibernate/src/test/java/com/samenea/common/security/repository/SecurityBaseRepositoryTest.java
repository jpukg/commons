package com.samenea.common.security.repository;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public abstract class SecurityBaseRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
}
