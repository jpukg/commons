package com.samenea.appfeature;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * <p>Will be used as follows:</p>
 * {@code
 *                 <sea:IsAppFeatureEnabled feature="chargeAccount">
 *                     {part of the page which should be
 *                     displayed based on feature status for example chargeAccount link }
 *                 </sea:IsAppFeatureEnabled>
 *}
 * <p>Note this tag is just used for displaying a feature link and options but
 * to prevent user from invoking a feature controller
 * controller should be annotated with {@link FeatureCheckTag}
 * and {@link FeatureSecurityInterceptor} should be configured properly to intercept access to
 * mvc controllers
 * @see FeatureService
 * @see FeatureSecurityInterceptor
 * @see FeatureCheckTag
 * @author: Jalal Ashrafi
 * Date: 4/8/13
 */
@Configurable
public class FeatureCheckTag extends ConditionalTagSupport {
    private static final Logger logger = LoggerFactory.getLogger(FeatureCheckTag.class);

    @Autowired
    FeatureService featureService;
    private String feature;


    @Override
    protected boolean condition() throws JspTagException {
        return featureService.isFeatureEnabled(feature);
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
