package com.samenea.commons.component.log.hibernateappender;


import com.samenea.commons.component.log.hibernateappender.hibernatesession.HibernateAppenderSessionService;
import com.samenea.commons.component.log.hibernateappender.hibernatesession.HibernateAppenderSessionServiceImpl;
import com.samenea.commons.component.log.hibernateappender.model.HibernateAppenderLoggingEvent;
import com.samenea.commons.component.log.hibernateappender.model.HibernateAppenderLoggingEventImpl;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

/**
 * Log4J appender that uses Hibernate to store log events in a database.
 * To use this appender, you must provide two properties in the Log4J
 * properties file:
 * <p/>
 * <UL>sessionServiceClass</UL>
 * <UL>loggingEventClass</UL>
 * <p/>
 * <P>The sessionServiceClass must implement the
 * with an open Hibernate session.  Your implementation of this interface
 * can perform any additional activities such as registering audit
 * interceptors if required.</P>
 * <p/>
 * <P>The loggingEventClass must implement the
 * {@link HibernateAppenderLoggingEvent} interface.  Using an interface for the
 * logging event leaves your implementation free to extend any
 * existing persistence ancestor that you may already be using.</P>
 * <p/>
 * <P>An example Log4J properties file to configure the HibernateAppender
 * would be:</P>
 * <p/>
 * <code>
 * ### direct messages to Hibernate<BR/>
 * log4j.appender.hibernate=HibernateAppender<BR/>
 * log4j.appender.hibernate.sessionServiceClass=HibernateHelper<BR/>
 * log4j.appender.hibernate.loggingEventClass=LogEvent<BR/>
 * </code>
 * <p/>
 * <P>You can now write a Hibernate mapping file for the class specified as
 * the <code>loggingEventClass</code> to persist whichever parts of the
 * logging event that you require.
 * </P>
 *
 * @author David Howe
 * @author Amar Mattey
 * @version 1.1
 */

/**
 * Log4J appender that uses Hibernate to store log events in a database.
 * To use this appender, you must provide two properties in the Log4J
 * properties file:
 * <p/>
 * <UL>sessionServiceClass</UL>
 * <UL>loggingEventClass</UL>
 * <p/>
 * <P>The sessionServiceClass must implement the
 * {@link HibernateAppenderSessionService} interface which provides the appender
 * with an open Hibernate session.  Your implementation of this interface
 * can perform any additional activities such as registering audit
 * interceptors if required.</P>
 * <p/>
 * <P>The loggingEventClass must implement the
 * {@link HibernateAppenderLoggingEvent} interface.  Using an interface for the
 * logging event leaves your implementation free to extend any
 * existing persistence ancestor that you may already be using.</P>
 * <p/>
 * <P>An example Log4J properties file to configure the HibernateAppender
 * would be:</P>
 * <p/>
 * <code>
 * ### direct messages to Hibernate<BR/>
 * log4j.appender.hibernate=HibernateAppender<BR/>
 * log4j.appender.hibernate.sessionServiceClass=HibernateHelper<BR/>
 * log4j.appender.hibernate.loggingEventClass=LogEvent<BR/>
 * </code>
 * <p/>
 * <P>You can now write a Hibernate mapping file for the class specified as
 * the <code>loggingEventClass</code> to persist whichever parts of the
 * logging event that you require.
 * </P>
 *
 * @author David Howe
 * @author Amar Mattey
 * @version 1.1
 */
@Configurable
public class HibernateAppender extends AppenderSkeleton implements Appender {
    private String url = "jdbc:h2:tcp://127.0.0.1/smsLogger";
    private String username = "sa";
    private String password = "";
    private String driver = "org.h2.Driver";
    private String dialect = "org.hibernate.dialect.H2Dialect";
    private String showSql = "false";
    private String hbm2ddl = "update";
    private boolean useJndiDataSource = false;
    private String jndiDataSource = "java:/oracleSmsLoggerDataSource";


    private   EntityManager session;

    private static final Vector<LoggingEvent> buffer = new Vector<LoggingEvent>();
    private static Boolean appending = Boolean.FALSE;

    /*
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    protected void append(LoggingEvent loggingEvent) {

        /* Ensure exclusive access to the buffer in case another thread is
         * currently writing the buffer.
         */
        synchronized (buffer) {
            // Add the current event into the buffer
            buffer.add(loggingEvent);
            /* Ensure exclusive access to the appending flag to guarantee that
             * it doesn't change in between checking it's value and setting it
             */
            synchronized (appending) {
                if (!appending.booleanValue()) {
                    /* No other thread is appending to the log, so this
                     * thread can perform the append
                     */
                    appending = Boolean.TRUE;
                } else {
                    /* Another thread is already appending to the log and it
                     * will take care of emptying the buffer
                     */
                    return;
                }
            }
        }

        try {
            EntityManager session = getSession();

            /* Ensure exclusive access to the buffer in case another thread is
             * currently adding to the buffer.
             */
            synchronized (buffer) {
                LoggingEvent bufferLoggingEvent;
                HibernateAppenderLoggingEvent loggingEventWrapper;

                /* Get the current buffer length.  We only want to process
                 * events that are currently in the buffer.  If events get
                 * added to the buffer after this point, they must have
                 * been caused by this loop, as we have synchronized on the
                 * buffer, so no other thread could be adding an event.  Any
                 * events that get added to the buffer as a result of this
                 * loop will be discarded, as to attempt to process them will
                 * result in an infinite loop.
                 */

                int bufferLength = buffer.size();

                for (int i = 0; i < bufferLength; i++) {
                    bufferLoggingEvent = buffer.get(i);


                    loggingEventWrapper = new HibernateAppenderLoggingEventImpl();


                    loggingEventWrapper.setMessage(bufferLoggingEvent.getRenderedMessage());
                    LocationInfo information = bufferLoggingEvent.getLocationInformation();
                    loggingEventWrapper.setClassName(information.getClassName());
                    loggingEventWrapper.setFileName(information.getFileName());
                    loggingEventWrapper.setLineNumber(information.getLineNumber());

                    Date logDate = new Date();
                    logDate.setTime(bufferLoggingEvent.timeStamp);
                    loggingEventWrapper.setLogDate(logDate);

                    loggingEventWrapper.setLoggerName(bufferLoggingEvent.getLoggerName());
                    loggingEventWrapper.setMethodName(information.getMethodName());

                    Date startDate = new Date();
                    startDate.setTime(LoggingEvent.getStartTime());
                    loggingEventWrapper.setStartDate(startDate);

                    loggingEventWrapper.setThreadName(bufferLoggingEvent.getThreadName());

                    if (bufferLoggingEvent.getThrowableStrRep() != null) {
                        for (int j = 0;
                             j < bufferLoggingEvent.getThrowableStrRep().length;
                             j++) {
                            loggingEventWrapper.addThrowableMessage(
                                    j,
                                    bufferLoggingEvent.getThrowableStrRep()[j]);
                        }
                    }

                    Level bufferLevel = bufferLoggingEvent.getLevel();
                    if (bufferLevel == null) {
                        loggingEventWrapper.setLevel("UNKNOWN");
                    } else {
                        loggingEventWrapper.setLevel(bufferLevel.toString());

                    }
                    if (loggingEventWrapper.getMessage().length() > 1000) {
                        loggingEventWrapper.setMessage(loggingEventWrapper.getMessage().substring(0, 999));
                    }
                    session.getTransaction().begin();
                    session.persist(loggingEventWrapper);
                   session.getTransaction().commit();
                }
//               session.flush();
                buffer.clear();

                /* Ensure exclusive access to the appending flag - this really
                 * shouldn't be needed as the only other check on this flag is
                 * also synchronized on the buffer.  We don't want to do this
                 * in the finally block as between here and the finally block
                 * we will not be synchronized on the buffer and another
                 * process could add an event to the buffer, but the appending
                 * flag will still be true, so that event would not get
                 * written until another log event triggers the buffer to
                 * be emptied.
                 */
                synchronized (appending) {
                    appending = Boolean.FALSE;
                }
            }
        } catch (HibernateException he) {
            logError("HibernateException", he, ErrorCode.GENERIC_FAILURE);
            // Reset the appending flag
            appending = Boolean.FALSE;
            session.getTransaction().rollback();
        }
    }

    /**
     * Lazy load hibernate session
     *
     * @return hibernate session
     */
    private EntityManager getSession() {
        if (session == null || !session.isOpen()) {
            session = new HibernateAppenderSessionServiceImpl().openSession(url, username, password, driver, dialect, showSql, hbm2ddl,useJndiDataSource,jndiDataSource);
        }
        return session;
    }

    /*
     * @see org.apache.log4j.Appender#close()
     */
    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    /*
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    public boolean requiresLayout() {
        return false;
    }


    /**
     * Sets the name of the class implementing the
     * {@link HibernateAppenderSessionService} interface.
     *
     * @param string qualified class name
     */


    /**
     * Log errors to log4j Error Handler in case the hibernate cannot write to datanase
     *
     * @param errorMessage    Error Message occurred during writing to database
     * @param accessException Exception raised
     * @param failure         failure code
     */
    private void logError(String errorMessage, Exception accessException, int failure) {
        errorHandler.error(errorMessage, accessException, failure);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getHbm2ddl() {
        return hbm2ddl;
    }

    public void setHbm2ddl(String hbm2ddl) {
        this.hbm2ddl = hbm2ddl;
    }

    public static Boolean getAppending() {
        return appending;
    }

    public static void setAppending(Boolean appending) {
        HibernateAppender.appending = appending;
    }

    public boolean isUseJndiDataSource() {
        return useJndiDataSource;
    }

    public void setUseJndiDataSource(boolean useJndiDataSource) {
        this.useJndiDataSource = useJndiDataSource;
    }

    public String getJndiDataSource() {
        return jndiDataSource;
    }

    public void setJndiDataSource(String jndiDataSource) {
        this.jndiDataSource = jndiDataSource;
    }
}