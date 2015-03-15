package com.samenea.commons.tracking.service;

import com.samenea.commons.tracking.model.Synonym;
import com.samenea.commons.tracking.model.Track;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/12/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TrackingService {


    Track record(String trackID, String description);


    List<Track> findTracks(String trackID);


    Synonym makeSynonym(String firstWord, String secondWord);
}
