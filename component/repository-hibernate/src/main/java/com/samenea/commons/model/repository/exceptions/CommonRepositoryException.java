package com.samenea.commons.model.repository.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonRepositoryException extends SamenException{
    public CommonRepositoryException() {
        super();    
    }

    public CommonRepositoryException(String s) {
        super(s);    
    }

    public CommonRepositoryException(String s, Throwable throwable) {
        super(s, throwable);    
    }

    public CommonRepositoryException(Throwable throwable) {
        super(throwable);    
    }
}
