package com.samenea.commons.utils.attempt;

import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

/**
 * It will be used to record number of attempts for an action
 * @author: Jalal Ashrafi
 * Date: 4/9/13
 */
@Configurable(preConstruction = true )
public class AttemptRecord implements AttemptRecordInfo {
    private static final Logger logger = LoggerFactory.getLogger(AttemptRecord.class);
    private int attemptCount = 0;
    /**
     * action identifier
     */
    private final String action;
    private Date lastAttemptTime;
    @Autowired
    Environment environment;

    /**
     * Creates an instance with {@link #attemptCount} == 0
     * @param action
     */
    public AttemptRecord(String action) {
        this.action = action;
        lastAttemptTime = environment.getCurrentDate();
    }

    @Override
    public int getAttemptCount() {
        return attemptCount;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void recordNewAttempt() {
        attemptCount ++;
        lastAttemptTime = environment.getCurrentDate();
    }

    @Override
    public String toString() {
        return "AttemptRecord{" +
                "attemptCount=" + attemptCount +
                ", action='" + action + '\'' +
                ", lastAttemptTime=" + lastAttemptTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttemptRecord that = (AttemptRecord) o;

        if (attemptCount != that.attemptCount) return false;
        if (!action.equals(that.action)) return false;
        if (!lastAttemptTime.equals(that.lastAttemptTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attemptCount;
        result = 31 * result + action.hashCode();
        result = 31 * result + lastAttemptTime.hashCode();
        return result;
    }
}
