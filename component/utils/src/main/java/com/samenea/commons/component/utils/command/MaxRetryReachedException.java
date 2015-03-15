package com.samenea.commons.component.utils.command;

/**
 * @author Jalal Ashrafi
 */
public class MaxRetryReachedException extends RuntimeException {
    private final int maxRetry;

    public MaxRetryReachedException(String message, int maxRetry) {
        super(message);
        this.maxRetry = maxRetry;
    }

    public int getMaxRetry() {
        return maxRetry;
    }
}
