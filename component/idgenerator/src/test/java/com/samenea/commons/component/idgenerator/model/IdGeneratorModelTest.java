package com.samenea.commons.component.idgenerator.model;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/30/12
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdGeneratorModelTest {

    @Test
    public void testConstractor() throws Exception {
        final String TOKEN = "sms";
        IdGeneratorModel sms = new IdGeneratorModel(TOKEN);
        Assert.assertEquals(Long.valueOf(0), sms.getSequenceID());
        Assert.assertEquals(TOKEN, sms.getToken());
        sms = new IdGeneratorModel(TOKEN, 500l);
        Assert.assertEquals(Long.valueOf(500), sms.getSequenceID());
        Assert.assertEquals(TOKEN, sms.getToken());
    }

    @Test
    public void testIncrementId() throws Exception {
        final String TOKEN = "sms";
        IdGeneratorModel sms = new IdGeneratorModel(TOKEN);
        sms.incrementId(503);
        Assert.assertEquals(Long.valueOf(503), sms.getSequenceID());
    }
}
