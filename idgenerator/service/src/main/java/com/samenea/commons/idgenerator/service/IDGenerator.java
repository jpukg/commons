package com.samenea.commons.idgenerator.service;

/**
 * Implementations of this interface encapsulate ID generation logic.
 * Currently there is only one implementation {@link HiLoGenerator}.
 * But only this public interface is exposed to clients to allow for the
 * possibility of different implementation later.
 */
public interface IDGenerator {
    /**
     * Returns the block size used for generating IDs. For example
     * if Hi value is 2 and block size is 100, then IDGenerator will reserve
     * 200 to 299 without any database operation.
     */
    Long getBlockSize();

    /**
     * Returns the name of the ID generator which this instance encapsulate.
     */
    String getName();

    /**
     * Returns the next ID sequence encapsulated by this interface in the form of {@link String}, rather than a {@link Long}.
     * This method should be thread-safe.
     */
    String getNextID();
}
