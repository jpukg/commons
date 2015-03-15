package com.samenea.commons.tracking.repository;

import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.tracking.model.Track;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TrackRepository  extends BasicRepository<Track,Long> {

   List<Track> findTracks(Set<String> synonymIDs);
}
