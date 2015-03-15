package com.samenea.commons.component.utils.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SamenRuntimeException extends RuntimeException{
    public SamenRuntimeException() {
        super();
    }

    public SamenRuntimeException(String s) {
        super(s);
    }

    public SamenRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SamenRuntimeException(Throwable throwable) {
        super(throwable);    
    }
}
