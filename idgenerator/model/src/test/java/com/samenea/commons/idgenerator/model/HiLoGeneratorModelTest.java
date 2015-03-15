package com.samenea.commons.idgenerator.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class HiLoGeneratorModelTest {
    @Test
    public void create_instance_with_default_values() {
        HiLoGeneratorModel model = new HiLoGeneratorModel();

        assertEquals(-1, model.getHi());
        assertEquals(HiLoGeneratorModel.DEFAULT_NAME, model.getName());

    }
    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_name_is_null() {
        new HiLoGeneratorModel(null, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_name_is_empty() {
        new HiLoGeneratorModel("", 10);
    }

    @Test
    public void should_be_equals_if_the_names_are_the_same() {
        HiLoGeneratorModel model1 = new HiLoGeneratorModel("sms", 10);
        HiLoGeneratorModel model2 = new HiLoGeneratorModel("sms", 100);

        assertEquals(model1, model2);
    }
}
