package com.samenea.commons.component.model.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/23/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryException extends CommonModelRuntimeException {
    public QueryException() {
        super();
    }

    public QueryException(String s) {
        super(s);
    }

    public QueryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public QueryException(Throwable throwable) {
        super(throwable);
    }
}
