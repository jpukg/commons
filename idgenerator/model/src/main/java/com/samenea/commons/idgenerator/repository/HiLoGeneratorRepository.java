package com.samenea.commons.idgenerator.repository;

import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.idgenerator.model.HiLoGeneratorModel;

/**
 * This interface defines the contract for HiLo repository.
 */
public interface HiLoGeneratorRepository extends BasicRepository<HiLoGeneratorModel, Long> {
    /**
     * Search database for specific sequence name.
     *
     * @param name The sequence name that must be searched in database.
     *
     * @return {@link HiLoGeneratorModel} that already saved in database with specified sequence name, {@code null}
     *      if sequence name does not exists.
     */
    HiLoGeneratorModel findByName(String name);

    /**
     * This method searches database for given sequence name. If it doesn't exists, this method creates a new record in database
     * with {@code Hi} value assigned to {@code 0}. otherwise it increments the previous {@code Hi} atomically.
     *
     * @param name The sequence name
     * @return The {@link HiLoGeneratorModel} already saved in database.
     */
    HiLoGeneratorModel getNextHi(String name);
}
