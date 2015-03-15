package com.samenea.commons.component.model.exceptions;

/**
 * A reusable Exception
 * @author Jalal Ashrafi
 */
public class DuplicateException extends CommonModelRuntimeException {
    /**
     * show already existing object which is equal to object for creating
     * currently it allows null
     */
    private final Object alreadyExistingObject;

    public DuplicateException(String message,Object alreadyExistingObject) {
        super(message);
        this.alreadyExistingObject = alreadyExistingObject;
    }

    public DuplicateException(String message, Throwable throwable, Object alreadyExistingObject) {
        super(message, throwable);
        this.alreadyExistingObject = alreadyExistingObject;
    }

    /**
     * show already existing object which is equal to object for creating
     * currently it allows null
     */
    public Object getAlreadyExistingObject() {
        return alreadyExistingObject;
    }
}
