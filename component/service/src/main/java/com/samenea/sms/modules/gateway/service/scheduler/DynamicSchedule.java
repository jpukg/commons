package com.samenea.sms.modules.gateway.service.scheduler;

import com.samenea.commons.component.config.ConfigPlaceHolder;
import com.samenea.commons.component.utils.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DynamicSchedule implements Trigger {
    private Logger log = LoggerFactory.getLogger(DynamicSchedule.class);

    @Autowired
    private ConfigPlaceHolder configPlaceHolder;
    private final String placeholderName;
    private String schedulerName = "";

    public DynamicSchedule(String placeholderName) {
        this.placeholderName = placeholderName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        AssertUtil.notNull(placeholderName, IllegalArgumentException.class);
        String interval = configPlaceHolder.resolveString(placeholderName);

        AssertUtil.notNull(interval, IllegalArgumentException.class);
        Date lastTime = triggerContext.lastCompletionTime();
        Long next = null;
        try {
            next = Long.valueOf(interval);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        Date nextExecutionTime = (lastTime == null)
                ? new Date()
                : new Date(lastTime.getTime() + next);
        if (log.isInfoEnabled()) {
            log.info("{} -- delays for {} ms nextExecutionTime is  ", schedulerName, next, new SimpleDateFormat("hh-MM-ss").format(nextExecutionTime));

        }
        return nextExecutionTime;
    }

}