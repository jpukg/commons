package com.samenea.appfeature;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * currently it works just on a configuration file property
 * @author: Jalal Ashrafi
 * Date: 4/8/13
 */
@Component
public class FeatureSecurityServiceImpl implements FeatureService {
    private static final Logger logger = LoggerFactory.getLogger(FeatureSecurityServiceImpl.class);
    public static final String SEPARATOR = ",";
    private final Set<String> enabledFeatures;

    @Autowired
    public FeatureSecurityServiceImpl(@Value("${application.enabledFeatures}") String featureNames) {
        enabledFeatures = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(featureNames.split(SEPARATOR))));
        logger.info("Application enabled features are: {}",featureNames);
    }

    @Override
    public boolean isFeatureEnabled(String featureName) {
        return enabledFeatures.contains(featureName);
    }
}
