package com.samenea.commons.component.log.hibernateappender.hibernatesession;

import org.hibernate.Session;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;

/**
 * @author David Howe
 * @version 1.1
 */
public interface HibernateAppenderSessionService {

    /**
     * <P>Returns a reference to a Hibernate session instance.</P>
     * <p/>
     * <P>This interface gives applications the ability to open a session
     * using their existing infrastructure, which may include registering
     * audit interceptors if required.</P>
     *
     * @param url
     * @param username
     * @param password
     * @param driver
     * @param dialect
     * @param showSql
     * @param hbm2ddl
     * @param useJndiDataSource
     * @param jndiDataSource
     * @return
     * @throws HibernateException
     */
    public EntityManager openSession(String url, String username, String password, String driver, String dialect, String showSql, String hbm2ddl, boolean useJndiDataSource, String jndiDataSource) throws HibernateException;
}
