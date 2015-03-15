package com.samenea.commons.component.log.hibernateappender;


import com.samenea.commons.component.log.hibernateappender.model.HibernateAppenderLoggingEvent;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.hibernate.HibernateException;
import org.slf4j.MDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * {@link com.samenea.commons.component.log.hibernateappender.model.HibernateAppenderLoggingEvent} interface.  Using an interface for the
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
public class JdbcAppender extends AppenderSkeleton implements Appender {


    private Class<HibernateAppenderLoggingEvent> loggingEvent=HibernateAppenderLoggingEvent.class;



    private static final Vector<LoggingEvent> buffer = new Vector<LoggingEvent>();
    private static Boolean appending = Boolean.FALSE;
    private String url = "jdbc:h2:tcp://127.0.0.1/smsLogger";
    private String username = "sa";
    private String password = "";
    private String driver = "org.h2.Driver";

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

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
//            Session session = getSession();

            /* Ensure exclusive access to the buffer in case another thread is
             * currently adding to the buffer.
             */
            synchronized (buffer) {
                LoggingEvent bufferLoggingEvent;
                HibernateAppenderLoggingEvent loggingEventWrapper=null;

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

                    try {
                        loggingEventWrapper =
                                this.loggingEvent
                                        .newInstance();
                    } catch (IllegalAccessException iae) {
                        logError("Unable to instantiate class " + this.loggingEvent
                                .getName(), iae, ErrorCode.GENERIC_FAILURE);
                        return;
                    } catch (InstantiationException ie) {
                        logError("Unable to instantiate class " + this.loggingEvent
                                .getName(), ie, ErrorCode.GENERIC_FAILURE);
                        return;
                    }

                    loggingEventWrapper.setMessage(bufferLoggingEvent.getRenderedMessage());
                    LocationInfo information = bufferLoggingEvent.getLocationInformation();
                    loggingEventWrapper.setClassName(information.getClassName());
                    loggingEventWrapper.setFileName(information.getFileName());
                    loggingEventWrapper.setLineNumber(information.getLineNumber());
                    loggingEventWrapper.setUsername(MDC.get("username"));
                    loggingEventWrapper.setRemoteAddress(MDC.get("remoteAddress"));

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
//                    session.save(loggingEventWrapper);
                }
//                session.flush();
                //STEP 2: Register JDBC driver
                save(loggingEventWrapper);
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
        }
    }

    private void save(HibernateAppenderLoggingEvent loggingEventWrapper) {
        try {

            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            String sql="insert into logger (className, fileName, level, lineNumber, logDate, loggerName, message," +
                    " methodName, remoteAddress, startDate, threadName, username) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,loggingEventWrapper.getClassName());
            preparedStatement.setString(2,loggingEventWrapper.getFileName());
            preparedStatement.setString(3,loggingEventWrapper.getLevel());
            preparedStatement.setString(4,loggingEventWrapper.getLineNumber());
            preparedStatement.setDate(5, new java.sql.Date(loggingEventWrapper.getLogDate().getTime()));
            preparedStatement.setString(6,loggingEventWrapper.getLoggerName());
            preparedStatement.setString(7,loggingEventWrapper.getMessage());
            preparedStatement.setString(8,loggingEventWrapper.getMethodName());
            preparedStatement.setString(9,loggingEventWrapper.getRemoteAddress());
            preparedStatement.setDate(10,new java.sql.Date(loggingEventWrapper.getStartDate().getTime()));
            preparedStatement.setString(11,loggingEventWrapper.getThreadName());
            preparedStatement.setString(12,loggingEventWrapper.getUsername());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void close() {

    }


    public boolean requiresLayout() {
        return false;
    }




    private void logError(String errorMessage, Exception accessException, int failure) {
        errorHandler.error(errorMessage, accessException, failure);
    }


}
