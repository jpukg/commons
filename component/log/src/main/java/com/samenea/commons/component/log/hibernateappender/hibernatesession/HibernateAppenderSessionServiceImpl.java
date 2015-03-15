package com.samenea.commons.component.log.hibernateappender.hibernatesession;

import org.hibernate.HibernateException;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Amar
 * Date: Dec 13, 2008
 * Time: 12:38:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateAppenderSessionServiceImpl implements HibernateAppenderSessionService {
    public EntityManager openSession(String url, String username, String password, String driver, String dialect, String showSql, String hbm2ddl, boolean useJndiDataSource, String jndiDataSource) throws HibernateException {

        DataSource driverManagerDataSource = dataSourceFactory(url, username, password, driver,useJndiDataSource,jndiDataSource);
        Properties properties = createProperty(url, username, password, driver, dialect, showSql, hbm2ddl);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.samenea.commons.component.log.hibernateappender.model");
        factoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        factoryBean.setPersistenceUnitName("logPM");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(properties);
        factoryBean.setDataSource(driverManagerDataSource);
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory().createEntityManager();
    }

    private DataSource dataSourceFactory(String url, String username, String password, String driver, boolean useJndiDataSource, String jndiDataSource) {
        if (!useJndiDataSource) {
            return new DriverManagerDataSource(driver, url, username, password);
        }
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName(jndiDataSource);
        try {
            jndiObjectFactoryBean.afterPropertiesSet();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return (DataSource) jndiObjectFactoryBean.getObject();
    }

    private Properties createProperty(String url, String username, String password, String driver, String dialect, String showSql, String hbm2ddl) {
        Properties properties = new Properties();
        properties.put("dialect", dialect);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.connection.driver_class", driver);
        properties.put("hibernate.connection.url", url);
        properties.put("hibernate.connection.username", username);
        properties.put("hibernate.connection.password", password);
        properties.put("hibernate.connection.autocommit", "true");
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
        return properties;
    }
}
