package com.samenea.commons.model.repository;
import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.component.model.query.Expression;
import com.samenea.commons.component.model.query.Paging;
import com.samenea.commons.component.model.query.Range;
import com.samenea.commons.component.model.query.enums.Operator;
import com.samenea.commons.component.model.query.enums.SortOrder;
import com.samenea.commons.component.utils.CollectionUtils;
import com.samenea.commons.model.repository.mock.MockEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 6/13/12
 * Time: 1:49 PM
 */

public class BasicRepositoryTestCase extends BaseRepositoryTestCase {
    Log log = LogFactory.getLog(BasicRepositoryTestCase.class);
    @Autowired
    private BasicRepository<MockEntity, Long> mockEntityRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void getAll() {
        final List<MockEntity> all = mockEntityRepository.getAll();
        assertEquals(13, all.size());
    }

    @Test
    public void testGetById() {
        final long AVAILABLE_ENTITY_ID = -1L;
        final MockEntity mockEntity = mockEntityRepository.
                get(AVAILABLE_ENTITY_ID);
        assertNotNull(mockEntity);
        Assert.assertEquals(AVAILABLE_ENTITY_ID, mockEntity.getId().longValue());
    }

    @Test(expected = ObjectRetrievalFailureException.class)
    public void testGetByIdNotFound() {
        final long UNAVAILABLE_ENTITY_ID = -10000L;
        mockEntityRepository.get(UNAVAILABLE_ENTITY_ID);
    }

    @Test
    public void testStore() {
        MockEntity mockEntity = new MockEntity("mock14");
        final MockEntity savedMock = mockEntityRepository.store(mockEntity);
        assertNotNull(savedMock);
        assertNotNull(savedMock.getId());
    }

    @Test
    public void testExist() {
        final long AVAILABLE_ID = -1L;
        assertNotNull(mockEntityRepository.exists(AVAILABLE_ID));
        assertTrue(mockEntityRepository.exists(AVAILABLE_ID));
        final long UNAVAILABLE_ID = -10000L;
        Assert.assertFalse(mockEntityRepository.exists(UNAVAILABLE_ID));
    }

    @Test
    public void testRemove() {
        final long AVAILABLE_ID = -1L;
        mockEntityRepository.remove(AVAILABLE_ID);
        try {
            mockEntityRepository.get(AVAILABLE_ID);
            Assert.fail("Entity Already removed");
        } catch (ObjectRetrievalFailureException e) {
            log.debug("Test OK");
        }

    }

    @Test(expected = ObjectRetrievalFailureException.class)
    public void testRemoveNotFound() {
        final long UNAVAILABLE_ENTITY_ID = -10000L;
        mockEntityRepository.get(UNAVAILABLE_ENTITY_ID);
    }

    @Ignore
    @Test(expected = DataIntegrityViolationException.class)
    public void testStoreDuplicate() {
        MockEntity duplicateEntity = new MockEntity("mock2");
        mockEntityRepository.store(duplicateEntity);
    }


    @Test
    public void findModelByExample() {
        MockEntity mock = new MockEntity("mock5");
        List<MockEntity> modelByExample = mockEntityRepository.findModelByExample(mock);
        Assert.assertEquals(1, modelByExample.size());
        Assert.assertEquals(5, modelByExample.get(0).getAge());
        mock = new MockEntity("mock");

        modelByExample = mockEntityRepository.findModelByExample(mock);
        Assert.assertEquals(13, modelByExample.size());
        mock = new MockEntity("mock");
        modelByExample = mockEntityRepository.findModelByExample(mock);
        Assert.assertEquals(13, modelByExample.size());
        mock = new MockEntity("mock");
        modelByExample = mockEntityRepository.findModelByExample(mock, new Paging(2, 5, "age", SortOrder.DESCENDING));
        Assert.assertEquals(5, modelByExample.size());
        Assert.assertEquals(11, modelByExample.get(0).getAge());
    }

    @Test
    public void getNumberOfModelsByExample() {
        MockEntity mock = new MockEntity("mock5");
        long size = mockEntityRepository.getNumberOfModelsByExample(mock);
        Assert.assertEquals(1, size);

        mock = new MockEntity("mock");
        size = mockEntityRepository.getNumberOfModelsByExample(mock);
        Assert.assertEquals(13, size);
        mock = new MockEntity("mock");
        size = mockEntityRepository.getNumberOfModelsByExample(mock);
        Assert.assertEquals(13, size);
        mock = new MockEntity("mock1");
        size = mockEntityRepository.getNumberOfModelsByExample(mock);
        Assert.assertEquals(5, size);

    }

    @Test
    public void findModelByExampleWithRange() {
        MockEntity mock = new MockEntity("");
        Expression age = new Expression("age", Operator.BETWEEN, 5, 9);
        List<Expression> expressions = CollectionUtils.paramsAsList(age);
        List<MockEntity> modelByExample = mockEntityRepository.findModelByExample(mock, expressions);
        Assert.assertEquals(5, modelByExample.size());
    }

    @Test
    public void getNumberOfModelsByExampleWithRange() {
        MockEntity mock = new MockEntity("");
        Expression age = new Expression("age", Operator.BETWEEN, 5, 9);
        List<Expression> expressions = CollectionUtils.paramsAsList(age);
        long size = mockEntityRepository.getNumberOfModelsByExample(mock, expressions);
        Assert.assertEquals(5, size);
    }

    @Test
    public void findModelByExampleExpressionGTTest() {
        MockEntity mock = new MockEntity("");
        Expression age = new Expression("age", Operator.GT, 9);
        List<MockEntity> modelByExample = mockEntityRepository.findModelByExample(mock, CollectionUtils.paramsAsList(age));
        Assert.assertEquals(4, modelByExample.size());
    }

}
