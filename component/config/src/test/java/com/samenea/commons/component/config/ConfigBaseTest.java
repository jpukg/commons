package com.samenea.commons.component.config;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 8/21/12
 * Time: 12:50 PM
 */
@ContextConfiguration(locations = {
        "classpath:/applicationContext-config/resource.xml"
})
public abstract class ConfigBaseTest extends AbstractJUnit4SpringContextTests {
}
