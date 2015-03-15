package com.samenea.commons.tracking.repository;

import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import com.samenea.commons.tracking.model.Synonym;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/14/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SynonymRepositoryHibernate extends BasicTrackingRepositoryHibernate<Synonym,Long> implements SynonymRepository{

    public SynonymRepositoryHibernate() {
        super(Synonym.class);
    }



    @Override
    public Synonym findBySynonymElement(String synonymElement) {

        Query query = getSession().createSQLQuery("select * from xsynonym xs join synonym_elements es on xs.id = es.synonym_id where es.synonymelements =:element").addEntity(Synonym.class);
        query.setParameter("element", synonymElement);

        List<Synonym> result = query.list();
        Synonym foundSynonym = null;
        if(result != null && result.size() > 0){
            foundSynonym = result.get(0);
        }

        return foundSynonym;
    }



}
