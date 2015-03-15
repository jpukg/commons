package com.samenea.commons.component.idgenerator.repository.hibernate;

import com.samenea.commons.component.AbstractHibernateAcceptanceTest;
import com.samenea.commons.component.idgenerator.model.IdGeneratorModel;
import com.samenea.commons.component.idgenerator.repository.IdGeneratorRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.annotation.DataSets;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/30/12
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
@DataSets(setUpDataSet = "/sample-data.xml")

public class IdGeneratorRepositoryHibernateTest  extends AbstractHibernateAcceptanceTest{
    @Autowired
    private IdGeneratorRepository idGeneratorRepository;

    @Test
    public void testGetAll() {
        List<IdGeneratorModel> all = idGeneratorRepository.getAll();
        Assert.assertEquals(4, all.size());

    }
    @Test
    public void testNew() {
        List<IdGeneratorModel> all = idGeneratorRepository.getAll();
        Assert.assertEquals(4, all.size());
        IdGeneratorModel aNew = idGeneratorRepository.store(new IdGeneratorModel("new"));
        Assert.assertNotNull(aNew.getId());
        all = idGeneratorRepository.getAll();
        Assert.assertEquals(5, all.size());
    }
    @Test
    public void testFindByToken() {
        IdGeneratorModel token4 = idGeneratorRepository.findByToken("token4");
        Assert.assertNotNull(token4);
        Assert.assertEquals(Long.valueOf(1000000000), token4.getSequenceID());

    }
}
