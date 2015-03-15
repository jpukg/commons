package com.samenea.commons.idgenerator.repository;

import com.samenea.commons.idgenerator.model.HiLoGeneratorModel;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.annotation.DataSets;

public class HiLoGeneratorRepositoryHibernateTest extends AbstractHiLoGeneratorRepositoryTest {
    @Autowired
    private HiLoGeneratorRepository repository;

    @Test
    @DataSets(setUpDataSet = "/test-data/empty-table.xml")
    public void getNextHi_should_create_a_new_one_if_there_is_not_matching_sequence_name() {
        repository.getNextHi("sample");

        HiLoGeneratorModel model = repository.findByName("sample");

        assertNotNull(model);
        assertEquals(0, model.getHi());
        assertEquals("sample", model.getName());
    }

    @Test
    @DataSets(setUpDataSet = "/test-data/sample-data.xml")
    public void getNextHi_should_increase_hi_value_if_sequence_name_exists() {
        repository.getNextHi("sms");

        HiLoGeneratorModel model = repository.findByName("sms");

        assertNotNull(model);
        assertEquals(3, model.getHi());
    }
}
