package com.samenea.commons.tracking.repository;

import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import com.samenea.commons.tracking.model.Track;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/18/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BasicTrackingRepositoryHibernate<T, PK extends Serializable>  extends BasicRepositoryHibernate<T, PK>  {

    public BasicTrackingRepositoryHibernate(final Class<T> persistentClass) {
       super(persistentClass);
    }

    @Override
    @Autowired()
    @Qualifier("trackingSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

}
