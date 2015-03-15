package com.samenea.commons.utils.attempt;

import java.util.Date;

/**
 * A read only view on {@link AttemptRecord}
 * @see AttemptRecord
 * @author: Jalal Ashrafi
 * Date: 4/9/13
 */
public interface AttemptRecordInfo {
    int getAttemptCount();

    String getAction();

    Date getLastAttemptTime();
}
