package com.samenea.commons.tracking.service;

import com.samenea.commons.tracking.model.Synonym;
import com.samenea.commons.tracking.model.Track;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/12/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackingServiceTest extends BaseTrackingServiceTest {
    @Autowired
    TrackingService trackingService;

    @Test
    public void test_find_tracks(){

       final List<Track> tracks = trackingService.findTracks("trackID_test_1");
       Assert.assertEquals(1,tracks.size());
    }

    @Test
    public void should_save_track_with_subsystem_name_set_in_properties_file(){

        Track savedTrack = trackingService.record("trn_001", "description_001");
        Assert.assertNotNull(savedTrack);
        Assert.assertEquals("test_subSystem",savedTrack.getSubSystem());
    }

    @Test
    public void should_make_synonym_two_word_when_does_not_exist_no_synonym_for_no_one(){
        Synonym newSynonym = trackingService.makeSynonym("id3", "id4");

        Assert.assertNotNull(newSynonym);
        Assert.assertEquals(2, newSynonym.getSynonymElements().size());
        Assert.assertTrue(newSynonym.getSynonymElements().contains("id3"));
        Assert.assertTrue(newSynonym.getSynonymElements().contains("id4"));
    }

    @Test
    public void should_make_synonym_two_words_when_their_synonym_already_exist(){
        Synonym existSynonym = trackingService.makeSynonym("id1", "id2");

        Assert.assertNotNull(existSynonym);
        Assert.assertEquals(-1L, existSynonym.getId().longValue());
    }

    @Test
    public void should_add_new_word_to_exist_synonym_for_first_word(){
        Synonym existSynonym = trackingService.makeSynonym("id1", "id5");

        Assert.assertNotNull(existSynonym);
        Assert.assertEquals(-1L, existSynonym.getId().longValue());
        Assert.assertEquals(3, existSynonym.getSynonymElements().size());
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id1"));
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id2"));
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id5"));
    }

    @Test
    public void should_add_new_word_to_exist_synonym_for_second_word(){
        Synonym existSynonym = trackingService.makeSynonym("id6", "id7");

        Assert.assertNotNull(existSynonym);
        Assert.assertEquals(-2L, existSynonym.getId().longValue());
        Assert.assertEquals(3, existSynonym.getSynonymElements().size());
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id7"));
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id8"));
        Assert.assertTrue(existSynonym.getSynonymElements().contains("id6"));
    }








}
