package com.samenea.appfeature;

/**
 * Central Service for checking feature status
 * @author: Jalal Ashrafi
 * Date: 4/8/13
 */
public interface FeatureService {
    public boolean isFeatureEnabled(String featureName);
}
