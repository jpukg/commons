package com.samenea.commons.idgenerator.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class has a single factory method that produce ID generator for clients.
 * When you get an ID generator, you should give it a name, and the system guarantees that each IDs that
 * will be generated under this name is unique.
 * The same instance will always return for the same sequence name.
 */
@Service
public class IDGeneratorFactory implements ApplicationContextAware {
    private Map<String, IDGenerator> generators = new HashMap<String, IDGenerator>();
    private ApplicationContext applicationContext;

    @Value("${generator}")
    private String generatorStrategy;

    @Value("${block_size}")
    private long blockSize;

    public synchronized IDGenerator getIDGenerator(String name)
    {
        Assert.notNull(name);
        Assert.hasText(name);

        if (!generators.containsKey(name)) {
            HiLoGenerator generator = (HiLoGenerator) applicationContext.getBean(generatorStrategy, name, blockSize);

            if (generator == null)
                throw new NoSuchGeneratorException(String.format("Generator '%s' has not been defined.", generatorStrategy));

            generators.put(name, generator);
        }

        return generators.get(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
