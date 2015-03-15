package com.samenea.commons.component.model.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonModelException extends SamenException{
    public CommonModelException() {
        super();    
    }

    public CommonModelException(String s) {
        super(s);    
    }

    public CommonModelException(String s, Throwable throwable) {
        super(s, throwable);    
    }

    public CommonModelException(Throwable throwable) {
        super(throwable);    
    }
}
