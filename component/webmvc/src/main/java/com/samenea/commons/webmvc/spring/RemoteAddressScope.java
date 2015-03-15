package com.samenea.commons.webmvc.spring;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>A custom scope that assigns a bean to a remote client.
 * Remote address is currently based on {@link javax.servlet.http.HttpServletRequest#getRemoteAddr()}
 * <p>To be used it should be registered in spring context:</p>
     <pre class="code"><code class="xml">
    {@code
    <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
        <property name="scopes">
            <map>
            <entry key="remoteAddress">
                <bean class="com.samenea.commons.webmvc.spring.RemoteAddressScope"/>
             </entry>

            </map>
       </property>
    </bean>
    }
 * </code></pre>
 * @author: Jalal Ashrafi
 * Date: 4/11/13
 */
public class RemoteAddressScope implements Scope  {
    private static final Logger logger = LoggerFactory.getLogger(RemoteAddressScope.class);
    private  final ConcurrentHashMap<String,Map<String,Object>> addressToObjectsMap =  new ConcurrentHashMap<String, Map<String, Object>>();

    public RemoteAddressScope() {
    }

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        final String remoteIp = getRemoteAddr();
        Map<String, Object> currentAddressObjects = getRemoteAddressObjects(remoteIp);

        Object object = currentAddressObjects.get(name);

        if(object==null){
            logger.debug("Object with name {} created for {} scope",name, remoteIp);
            object = objectFactory.getObject();
            currentAddressObjects.put(name, object);
        }else {
            logger.debug("Object  with name {} already exist for {} scope",name, remoteIp);
        }
        return object;
    }

    private Map<String, Object> getRemoteAddressObjects(String remoteIp) {
        Map<String,Object> currAddressObjects = addressToObjectsMap.get(remoteIp);

        if(currAddressObjects == null){
            logger.debug("There is no object for created for address {} scope",remoteIp);
            currAddressObjects = new ConcurrentHashMap<String, Object>();
            addressToObjectsMap.put(remoteIp, currAddressObjects);
        }
        return currAddressObjects;
    }

    private String getRemoteAddr() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
    }

    @Override
    public Object remove(String name) {
//        todo implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getConversationId() {
        logger.debug("getConversationId for {} scope called.",getRemoteAddr());
        return getRemoteAddr();
    }
}
