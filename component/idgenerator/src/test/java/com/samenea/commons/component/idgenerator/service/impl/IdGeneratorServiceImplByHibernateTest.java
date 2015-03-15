package com.samenea.commons.component.idgenerator.service.impl;

import com.samenea.commons.component.AbstractHibernateAcceptanceTest;
import com.samenea.commons.component.idgenerator.model.IdGeneratorModel;
import com.samenea.commons.component.idgenerator.repository.IdGeneratorRepository;
import com.samenea.commons.component.idgenerator.service.IdGeneratorService;
import com.samenea.commons.component.utils.RandomUtil;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.annotation.DataSets;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/22/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class IdGeneratorServiceImplByHibernateTest extends AbstractHibernateAcceptanceTest {
    @Autowired
    private IdGeneratorService idGenerator;
    @Autowired
    private IdGeneratorRepository idGeneratorRepository;

    @Before
    public void setUp() throws Exception {


    }

    /* @Test
    @DataSets(setUpDataSet = "/sample-data.xml")
    public void testGetNextId() throws Exception {
        long l = System.currentTimeMillis();

        for (int i = 1; i < IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE - 1; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId());
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId());
        }
        IdGeneratorModel byToken = idGeneratorRepository.findByToken("");
        Assert.assertEquals(Long.valueOf(IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE), byToken.getSequenceID());
        String token = RandomUtil.randomString();
        for (long i = IdGeneratorModel.DEFAULT_INIT_ID + 1; i < IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE - 1; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId(token));
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId(token));
        }
        byToken = idGeneratorRepository.findByToken(token);
        Assert.assertEquals(Long.valueOf(IdGeneratorModel.DEFAULT_INIT_ID + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE), byToken.getSequenceID());

        for (int i = 801; i < 800 + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE - 1  ; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId("token3"));
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId("token3"));

        }
        byToken = idGeneratorRepository.findByToken("token3");
        Assert.assertEquals(Long.valueOf(800 + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE), byToken.getSequenceID());
        System.out.println(System.currentTimeMillis() - l);


    }*/

    @Ignore
    @Test
    @DataSets(setUpDataSet = "/sample-data.xml")
    public void testGetNextIdAndUpdateDB() throws Exception {


        for (int i = 1; i < IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE + 1; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId());
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId());
        }
        IdGeneratorModel byToken = idGeneratorRepository.findByToken(IdGeneratorServiceImpl.DEFAULT_GLOBAL_TOKEN_NAME);
        Assert.assertEquals(Long.valueOf(IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE * 2), byToken.getSequenceID());
        String token = RandomUtil.randomString();
        for (long i = IdGeneratorModel.DEFAULT_INIT_ID + 1; i < IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE + 1; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId(token));
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId(token));
        }
        byToken = idGeneratorRepository.findByToken(token);
        Assert.assertEquals(Long.valueOf(IdGeneratorModel.DEFAULT_INIT_ID + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE * 2), byToken.getSequenceID());

        for (int i = 801; i < 800 + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE + 1; i++) {
            Assert.assertEquals(String.valueOf(i), idGenerator.getNextId("token3"));
            Assert.assertEquals(String.valueOf(i), idGenerator.getCurrentId("token3"));

        }
        byToken = idGeneratorRepository.findByToken("token3");
        Assert.assertEquals(Long.valueOf(800 + IdGeneratorServiceImpl.DEFAULT_CACHE_SIZE * 2), byToken.getSequenceID());

    }
}
