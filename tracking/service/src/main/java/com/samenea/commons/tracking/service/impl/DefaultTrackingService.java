package com.samenea.commons.tracking.service.impl;

import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.tracking.model.Synonym;
import com.samenea.commons.tracking.model.Track;
import com.samenea.commons.tracking.repository.SynonymRepository;
import com.samenea.commons.tracking.repository.TrackRepository;
import com.samenea.commons.tracking.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/12/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultTrackingService implements TrackingService {
    @Autowired
    TrackRepository trackRepository;

    @Autowired
    SynonymRepository synonymRepository;

    @Value("${subsystem.name}")
    String subSystemName;

    @Autowired
    Environment environment;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW , value = "trackingTransactionManager")
    public Track record(String trackID, String description) {

        return trackRepository.store(new Track(subSystemName, environment.getCurrentDate(), description, trackID));
    }

    @Override
    @Transactional(readOnly = true, value = "trackingTransactionManager")
    public List<Track> findTracks(String trackID) {
        Synonym synonym = synonymRepository.findBySynonymElement(trackID);
        Set<String> idsForSearch = null;
        if(synonym == null){
            idsForSearch = new HashSet<String>();
            idsForSearch.add(trackID);
        }else{
            idsForSearch = synonym.getSynonymElements();
        }

        return trackRepository.findTracks(idsForSearch);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = "trackingTransactionManager")
    public Synonym makeSynonym(String firstWord, String secondWord) {
        Synonym firstSynonym =  synonymRepository.findBySynonymElement(firstWord);
        Synonym secondSynonym =  synonymRepository.findBySynonymElement(secondWord);

        Synonym finalSynonym = null;

        if(firstSynonym == null && secondSynonym == null){
           finalSynonym = new Synonym(firstWord, secondWord);
           synonymRepository.store(finalSynonym);

        }else if(firstSynonym != null && secondSynonym != null){
           if(!firstSynonym.equals(secondSynonym)){
               throw new IllegalStateException(String.format("these words : %s, %s have two inconsistent sysnonyms!",firstWord, secondWord));
           }

            finalSynonym = firstSynonym;

        }else{
            if(firstSynonym == null){
                finalSynonym = secondSynonym;
                finalSynonym.addSynonymElement(firstWord);

            }else{
                finalSynonym = firstSynonym;
                finalSynonym.addSynonymElement(secondWord);

            }

            synonymRepository.store(finalSynonym);
        }

        return finalSynonym;
    }


}
