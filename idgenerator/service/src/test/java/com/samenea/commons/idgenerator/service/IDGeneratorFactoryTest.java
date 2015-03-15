package com.samenea.commons.idgenerator.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class IDGeneratorFactoryTest extends AbstractIDGeneratorTest {
	@Autowired
	private IDGeneratorFactory factory;
	
    @Test
    public void should_return_an_instance_of_idgenerator() {
    	IDGenerator generator = factory.getIDGenerator("sample");
    	
    	assertNotNull(generator);
    	assertTrue(generator instanceof HiLoGenerator);
    	assertEquals("sample", generator.getName());
    }
    
    @Test
    public void should_return_the_same_instance_for_the_same_sequence_name() {
    	IDGenerator generator1 = factory.getIDGenerator("sample");
    	IDGenerator generator2 = factory.getIDGenerator("sample");
    	
    	assertSame(generator1, generator2);
    }
    
    @Test
    public void should_return_different_instance_for_different_sequence_name() {
    	IDGenerator generator1 = factory.getIDGenerator("sample");
    	IDGenerator generator2 = factory.getIDGenerator("another-sample");
    	
    	assertNotSame(generator1, generator2);
    }
    
    @Test
    public void should_read_block_size_from_properties_file() {
    	IDGenerator generator = factory.getIDGenerator("sample");
    	
    	assertEquals((long)100, (long)generator.getBlockSize());
    }
}
