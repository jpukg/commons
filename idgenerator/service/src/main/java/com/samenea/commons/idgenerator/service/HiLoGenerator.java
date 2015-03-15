package com.samenea.commons.idgenerator.service;

import com.samenea.commons.idgenerator.model.HiLoGeneratorModel;
import com.samenea.commons.idgenerator.repository.HiLoGeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This class is the HiLo implementation of {@link IDGenerator}.It queries the database to find the state of current sequence.
 * (Hi value) and it generates IDs from {@code Hi*blockSize} to {@code Hi*(blockSize +1) - 1}. Note that larger block size
 * are more efficient, as this class has to make fewer database updates. However note that any IDs not yet assigned when
 * this instance is destroyed are simply lost, so large block sizes may also result in large "gaps" in the ID sequence.
 */
@Service
class HiLoGenerator implements IDGenerator {
    private String name;
    private Long currentHi;

    // Initialize this field with maximum value to ensure that every time
    // system starts, it gets a new Hi value from database. For example
    // in system crash, or restart, it guarantees that we produce new IDs
    // different from the previous ones.
    private Long currentLo = Long.MAX_VALUE;

    private Long blockSize;

    @Autowired
    private HiLoGeneratorRepository repository;

    HiLoGenerator() {
    }

    public HiLoGenerator(String name, Long blockSize) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must not be empty");
        Assert.isTrue(blockSize > 0, "blockSize should be greater than zero.");

        this.name = name;
        this.blockSize = blockSize;
    }

    public String getName() {
        return this.name;
    }

    public Long getBlockSize() {
        return this.blockSize;
    }

    public synchronized String getNextID() {
        if (currentLo >= blockSize) {
            HiLoGeneratorModel model = repository.getNextHi(this.getName());
            currentHi = model.getHi();
            currentLo = 0L;
        }

        Long result = currentHi*blockSize + currentLo;
        currentLo++;

        return result.toString();
    }
}

