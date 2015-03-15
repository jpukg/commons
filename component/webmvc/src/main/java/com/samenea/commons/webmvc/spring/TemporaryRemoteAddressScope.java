package com.samenea.commons.webmvc.spring;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.utils.spring.TemporaryScope;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>A custom scope that assigns a bean to a remote client temporarily (based on the configured timeout).
 * Remote address is currently based on X_Forwarded_For http header if exist, otherwise {@link javax.servlet.http.HttpServletRequest#getRemoteAddr()}
 * <p>To be used it should be registered in spring context:</p>
    <pre class="code"><code class="xml">
     {@code
     <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
        <property name="scopes">
            <map>
                <entry key="temporaryRemoteAddress">
                    <bean class="com.samenea.commons.webmvc.spring.TemporaryRemoteAddressScope"/>
                </entry>
            </map>
        </property>
     </bean>
     }
 * </code></pre>
 * @see TemporaryScope
 * @see RemoteAddressScope
 * @author: Jalal Ashrafi
 * Date: 4/17/13
 */
public class TemporaryRemoteAddressScope extends TemporaryScope {
    private static final Logger logger = LoggerFactory.getLogger(TemporaryRemoteAddressScope.class);
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     *
     * @return this ip address of client for the current request
     * @see RequestContextHolder
     */
    @Override
    protected String getScope() {
        // On production server we had remote address of proxy thus we use X-Forwarded-For instead
        final String X_Forwarded_For = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(X_FORWARDED_FOR);
        if(X_Forwarded_For != null){
            logger.debug(X_FORWARDED_FOR + " exist.");
            return X_Forwarded_For;
        }else{
            logger.debug(X_FORWARDED_FOR + "  does not exist, so returning remoteAddress instead.");
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
        }
    }

    /**
     *
     * @param scopeExpireTimeInSeconds after this time passed from last access time to this scope the scope
     *                                 will be cleared automatically
     */
    public TemporaryRemoteAddressScope(long scopeExpireTimeInSeconds) {
        super(scopeExpireTimeInSeconds);
    }
}
