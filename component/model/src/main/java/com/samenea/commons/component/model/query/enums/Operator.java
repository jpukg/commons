package com.samenea.commons.component.model.query.enums;

import com.samenea.commons.component.model.exceptions.QueryException;

/**
 * Created by IntelliJ IDEA.
 * User: jee
 * Date: 8/17/11
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Operator {
    GT(1),
    EQ(1),
    NE(1),
    LE(1),
    GE(1),
    I_LIKE(1),
    LIKE(1),
    LT(1),
    SIZE_EQ(1),
    SIZE_NE(1),
    SIZE_GT(1),
    SIZE_LT(1),
    SIZE_GE(1),
    SIZE_LE(1),
    IS_NULL(0),
    IS_NOT_NULL(0),
    IS_NOT_EMPTY(0),
    IS_EMPTY(0),
    BETWEEN(2);
    private final int paramSize;

    public int getParamSize() {
        return paramSize;
    }

    private Operator(int paramSize) {
        this.paramSize = paramSize;
    }

    /**
     * @param paramSize
     * @throws QueryException
     */
    public void validateParamSize(int paramSize) throws QueryException {
        if (this.paramSize != paramSize) {
            throw new QueryException(paramSize + " param size is not satisfied by " + this);
        }
    }
}
