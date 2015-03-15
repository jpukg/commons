package com.samenea.commons.utils.attempt;

import com.google.common.collect.Maps;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentMap;

/**
 * @author: Jalal Ashrafi
 * Date: 4/9/13
 *
 */
public class RequestAttemptCounter {
    private static final Logger logger = LoggerFactory.getLogger(RequestAttemptCounter.class);
    private static final ThreadLocal<AttemptRecordInfo> lastAttemptInfoThreadLocal = new ThreadLocal<AttemptRecordInfo>();
    //    private final String contextId;
    private final ConcurrentMap<String,AttemptRecord> attemptRecordMap;

    public RequestAttemptCounter() {
        attemptRecordMap = Maps.newConcurrentMap();
//        contextId = null;
    }


    public static AttemptRecordInfo getLastAttemptInfoInCurrentThread(){
        return lastAttemptInfoThreadLocal.get();
    }

    /**
     * <p>records an attempt for the pa ssed in action (i.e increments attemptCount)
     * an places a this attempt as the last attempt info in a thread local variable
     *
     * <p>The last attempt info for the current thread can be accessed by {@link RequestAttemptCounter#getLastAttemptInfoInCurrentThread()}</p>
     * @param action
     * @return
     */
    public synchronized AttemptRecordInfo recordAttempt(String action) {
        //todo it is better to return a copy (by using a copy constructor for example)
        AttemptRecord attemptRecord = attemptRecordMap.get(action);
        if(attemptRecord == null){
            attemptRecord = new AttemptRecord(action);
            attemptRecordMap.put(action, attemptRecord);
        }
        attemptRecord.recordNewAttempt();
        lastAttemptInfoThreadLocal.set( attemptRecord);
        return attemptRecord;
    }

    /**
     * It just removes from recorded attempts list. Tt has nothing to do with threadlocal
     * registered one
     * @param action
     */
    public synchronized void removeAttemptRecord(String action) {
        attemptRecordMap.remove(action);
    }

    /**
     * <p>Removes The last attempt record in current thread
     * <p>This can be used in thread pools - for example - which a thread is reused across
     * several request. At the end of every request the info should be removed to prevent
     * conflicts
     */
    public static void removeLastAttemptInfoInCurrentThread() {
        if(lastAttemptInfoThreadLocal != null)
            lastAttemptInfoThreadLocal.remove();
    }

}

