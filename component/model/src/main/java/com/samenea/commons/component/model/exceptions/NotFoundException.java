package com.samenea.commons.component.model.exceptions;

/**
 * A reusable exception for using when an object can not be found
 * @author Jalal Ashrafi
 */
public class NotFoundException extends CommonModelRuntimeException {
    public NotFoundException(Throwable throwable) {
        super(throwable);
    }

    public NotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotFoundException(String s) {
        super(s);
    }
}
