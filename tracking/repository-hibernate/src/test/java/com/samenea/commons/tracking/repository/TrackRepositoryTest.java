package com.samenea.commons.tracking.repository;

import com.samenea.commons.tracking.model.Track;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import junit.framework.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackRepositoryTest extends BaseTrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;

    @Test
    public void test_saving_track(){
        Track newTrack = new Track("subsytem_1", new Date(), "description_1", "trn_001");
        Track savedTrack = trackRepository.store(newTrack);

        Assert.assertNotNull(savedTrack.getId());
    }

    @Test
    public void test_get_all(){
        List<Track> allTracks = trackRepository.getAll();
        Assert.assertNotNull(allTracks);

        final int expectedSize = 2;
        Assert.assertEquals(expectedSize, allTracks.size());
    }

    @Test
    public void test_get_by_id(){
       final Track track = trackRepository.get(-1L);

       Assert.assertEquals("description_test_1",track.getDescription());
       Assert.assertEquals("subSystem_test_1",track.getSubSystem());
       Assert.assertEquals("trackID_test_1",track.getTrackID());
    }

    @Test
    public void test_find_tracks(){
        Set<String> ids =  new HashSet<String>();
        ids.add("trackID_test_1");
        final List<Track> tracks = trackRepository.findTracks( ids);

        Assert.assertEquals("description_test_1", tracks.get(0).getDescription());
        Assert.assertEquals("subSystem_test_1",tracks.get(0).getSubSystem());
        Assert.assertEquals("trackID_test_1",tracks.get(0).getTrackID());

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_find_tracks_with_empty_trackIDs(){
        Set<String> ids =  new HashSet<String>();

        trackRepository.findTracks(ids);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_find_tracks_with_null_trackIDs(){

        trackRepository.findTracks( null);
    }

}
