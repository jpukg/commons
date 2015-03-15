package com.samenea.appfeature;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interceptor that checks the if the application feature associated with the controller is enabled or not
 * , if not it  sends HTTP status code 403 ("forbidden").
 * @author Jalal Ashrafi
 * @see FeatureService
 * @see ApplicationFeature
 */
@Configurable
public class FeatureSecurityInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(FeatureSecurityInterceptor.class);

    @Autowired
    FeatureService featureService;


    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {

        ApplicationFeature applicationFeature = null;
        if(handler.getClass().isAssignableFrom(HandlerMethod.class) ){
            applicationFeature = ((HandlerMethod) handler ).getBeanType().getAnnotation(ApplicationFeature.class);
        }
//        by default there is no restriction accessing controllers with no annotation
        if(applicationFeature == null){
            return true;
        }
        final String featureName = applicationFeature.value();
        if (!featureService.isFeatureEnabled(featureName)){
            logger.warn("User can not access this feature ('{}') but tries to. User ip is: {}",featureName, request.getRemoteAddr());
            handleNotAuthorized(request, response, handler);
            return false;
        }else{
            return true;
        }

    }

    /**
     * Handle a request that is not authorized according to this interceptor.
     * Default implementation sends HTTP status code 403 ("forbidden").
     * <p>This method can be overridden to write a custom message, forward or
     * redirect to some error page or login page, or throw a ServletException.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @throws javax.servlet.ServletException if there is an internal error
     * @throws java.io.IOException in case of an I/O error when writing the response
     */
    protected void handleNotAuthorized(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
