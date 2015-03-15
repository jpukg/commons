package com.samenea.commons.tracking.repository;


import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import com.samenea.commons.tracking.model.Track;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Repository()
public class TrackRepositoryHibernate extends BasicTrackingRepositoryHibernate<Track, Long> implements TrackRepository{



    public TrackRepositoryHibernate() {
        super(Track.class);
    }


    @Override
    public List<Track> findTracks(Set<String> synonymIDs) {
        Assert.notNull(synonymIDs, "for finding tracks trackIDs can't be specified!");
        Assert.isTrue(synonymIDs.size() > 0, "for finding tracks at least one trackID should be specified!");

        Query query = createFindTracksQuery(synonymIDs.size());

        Iterator<String> idsIterator = synonymIDs.iterator();
        for( int i = 0 ; i < synonymIDs.size(); i++){

            query.setParameter("id"+ i, idsIterator.next());
        }
       // query.setParameter("subSystem", subSystem);

        return query.list();
    }

    private Query createFindTracksQuery(int size){

        Set<String> queryVariables = new HashSet<String>();
        for( int i = 0; i < size; i++){
            queryVariables.add(":id" + i);
        }
        String joinedIds = StringUtils.join(queryVariables, ",");
        return getSession().createQuery("from Track where trackID in (" + joinedIds + ") order by occurrenceDate asc");

    }


}
