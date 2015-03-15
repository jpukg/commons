package com.samenea.commons.tracking.model;

import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackTest {
    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_empty_trackID(){
        new Track("subSystem_1", new Date(), "description_1", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_null_trackID(){
        new Track("subSystem_1", new Date(), "description_1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_null_subSystem(){
        new Track(null, new Date(), "description_1", "trackID_1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_empty_subSystem(){
        new Track("", new Date(), "description_1", "trackID_1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_empty_description(){
        new Track("subSystem_1", new Date(), "", "trackID_1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_with_null_description(){
        new Track("subSystem_1", new Date(), null, "trackID_1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_track_without_date(){
        new Track("subSystem_1", null, "description_1", "trackID_1");
    }

}
