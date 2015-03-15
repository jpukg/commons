package com.samenea.commons.utils.spring;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Could be used as a base class for temporary scope ( scope with timeout). After timeout seconds passed
 * from the last access time for the scope, the scope will be cleared and subsequent calls for getting
 * object will be resulted to creating new scope and consequently new objects.
 * <p>Subclasses should override {@link #getScope()}. and OPTIONALLY other nonfinal methods of {@link Scope}</p>
 *
 * @author: Jalal Ashrafi
 * Date: 4/17/13
 */
public abstract class TemporaryScope implements Scope {
    private final Cache<String, ConcurrentMap<String, Object>> cachedScopes;
    private static final Logger logger = LoggerFactory.getLogger(TemporaryScope.class);

    /**
     * create new scope instance
     *
     * @param scopeExpireTimeInSeconds after this time passed from the last access time to this scope, scope will
     *                                 be cleared
     */
    public TemporaryScope(long scopeExpireTimeInSeconds) {
        cachedScopes = CacheBuilder.newBuilder().
                expireAfterAccess(scopeExpireTimeInSeconds, TimeUnit.SECONDS).
                build();
        logger.debug("Cache created with Expire after access: {} seconds", scopeExpireTimeInSeconds);
    }

    //todo think about concurrency and performance, narrow synchronized span if possible ...
    @Override
    public final synchronized Object get(final String name, final ObjectFactory<?> objectFactory) {
        try {
            final String scope = getScope();
            logger.debug("getting scope: {}", scope);
            final ConcurrentMap<String, Object> cachedObjects = cachedScopes.get(scope, new Callable<ConcurrentMap<String, Object>>() {
                @Override
                public ConcurrentMap<String, Object> call() throws Exception {
                    logger.debug("new object map created for scope: {}", scope);
                    return Maps.newConcurrentMap();
                }
            });
            Object object = cachedObjects.get(name);
            if (object == null) {
                object = objectFactory.getObject();
                logger.debug("object with name: {} created for scope: {}", name, getScope());
                cachedObjects.put(name, object);
            }
            return object;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object remove(String name) {
        return null;
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
    public final String getConversationId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected abstract String getScope();
}