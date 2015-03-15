package com.samenea.commons.webmvc.listener;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.io.UnsupportedEncodingException;

/**
 * @author: Soroosh Sarabadani
 * Date: 4/7/13
 * Time: 7:01 PM
 * <p/>
 * A <code>ServletRequestListener</code> that set the encnding of current request to UTF-8.
 * Some times you will need to set encoding before any filters, then use this listener in your web.xml.
 */

public class UnicodeListener implements ServletRequestListener {
    private Logger logger = LoggerFactory.getLogger(UnicodeListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        try {
            servletRequestEvent.getServletRequest().setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }
}
