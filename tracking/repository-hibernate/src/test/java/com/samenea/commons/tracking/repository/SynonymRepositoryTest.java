package com.samenea.commons.tracking.repository;

import com.samenea.commons.tracking.model.Synonym;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/14/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class SynonymRepositoryTest extends BaseTrackRepositoryTest {

    @Autowired
    SynonymRepository synonymRepository;

    @Test
    public void test_create_synonym(){
        Synonym synonym = new Synonym("id1", "id2");
        Synonym savedSynonym = synonymRepository.store(synonym);

        Assert.assertNotNull(savedSynonym.getId());

    }

    @Test
    public void test_find_synonym_by_element(){
        Synonym synonym = synonymRepository.findBySynonymElement("id2");

        Assert.assertEquals(-1L, synonym.getId().longValue());
    }

}
