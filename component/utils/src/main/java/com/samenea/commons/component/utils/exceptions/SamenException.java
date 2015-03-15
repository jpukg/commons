package com.samenea.commons.component.utils.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SamenException extends Exception{
    public SamenException() {
        super();    
    }

    public SamenException(String s) {
        super(s);    
    }

    public SamenException(String s, Throwable throwable) {
        super(s, throwable);    
    }

    public SamenException(Throwable throwable) {
        super(throwable);    
    }
}
