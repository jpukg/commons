package com.samenea.commons.idgenerator.service;

import com.samenea.commons.idgenerator.model.HiLoGeneratorModel;
import com.samenea.commons.idgenerator.repository.HiLoGeneratorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class HiLoGeneratorTest extends AbstractIDGeneratorTest {
    private final Long BLOCK_SIZE = 10L;
    private final String SEQ_NAME = "sms";

    @Mock
    private HiLoGeneratorRepository repository;

    @InjectMocks
    private HiLoGenerator generator;

    @Before
    public void setup() {
        generator = new HiLoGenerator(SEQ_NAME, BLOCK_SIZE);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        // Act
        HiLoGenerator hiLoGenerator = new HiLoGenerator(SEQ_NAME, BLOCK_SIZE);

        // Assert
        assertNotNull(hiLoGenerator);
        assertEquals("sms", hiLoGenerator.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_sequence_name_is_null() {
        // Act
        new HiLoGenerator(null, BLOCK_SIZE);

        // Assert
        fail("Should throw exception if sequence name is null.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_sequence_name_is_empty() {
        // Act
        new HiLoGenerator("", BLOCK_SIZE);

        // Assert
        fail("Should throw exception if sequence name is empty.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_blocksize_is_zero() {
        // Act
        new HiLoGenerator(SEQ_NAME, 0L);

        // Assert
        fail("Should throw exception if block size is zero.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_blocksize_is_lessthan_zero() {
        // Act
        new HiLoGenerator(SEQ_NAME, -10L);

        // Assert
        fail("Should throw exception if block size is zero.");
    }

    @Test
    public void should_generate_ids_according_to_hi_value_and_block_size() {
        // Arrange
        HiLoGeneratorModel firstCall = new HiLoGeneratorModel(1, SEQ_NAME, 3);
        when(repository.getNextHi(SEQ_NAME)).thenReturn(firstCall);


        // Act
        String id1 = generator.getNextID();
        String id2 = generator.getNextID();

        // Assert
        assertEquals("30", id1);
        assertEquals("31", id2);
    }

    @Test
    public void should_get_new_hi_value_if_all_ids_in_this_block_has_been_taken() {
        // Arrange
        HiLoGeneratorModel firstCall = new HiLoGeneratorModel(1, SEQ_NAME, 3);
        HiLoGeneratorModel secondCall = new HiLoGeneratorModel(2, SEQ_NAME, 5);
        when(repository.getNextHi(SEQ_NAME)).thenReturn(firstCall).thenReturn(secondCall);
        for(int i = 0; i < BLOCK_SIZE; i++) {
            generator.getNextID();
        }

        // Act
        String newId = generator.getNextID();

        // Assert
        assertEquals("50", newId);
    }
}
