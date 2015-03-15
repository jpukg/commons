package com.samenea.commons.tracking.repository;

import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.tracking.model.Synonym;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/14/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SynonymRepository extends BasicRepository<Synonym, Long>{
    Synonym findBySynonymElement(String synonymElement);
}
