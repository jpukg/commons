package com.samenea.commons.tracking.model;


import com.samenea.commons.component.model.Entity;
import org.springframework.util.Assert;

import javax.persistence.Column;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/11/13
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
public class Track extends Entity<Long> {

    private String subSystem;
    private Date occurrenceDate;
    @Column(length = 1500)
    private String description;
    private String trackID;

    public String getSubSystem() {
        return subSystem;
    }

    public String getTrackID() {
        return trackID;
    }

    public String getDescription() {
        return description;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    private Track(){

    }

    public Track(String subSystem, Date occurrenceDate, String description, String trackID) {
        Assert.hasText(subSystem, "subSystem should be declared!");
        Assert.notNull(occurrenceDate, "occurrence time of event should be specified!");
        Assert.hasText(description, "description couldn't be empty!");
        Assert.hasText(trackID, "trackID couldn't be empty!");

        this.subSystem = subSystem;
        this.occurrenceDate = occurrenceDate;
        this.description = description;
        this.trackID = trackID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (!description.equals(track.description)) return false;
        if (!occurrenceDate.equals(track.occurrenceDate)) return false;
        if (!subSystem.equals(track.subSystem)) return false;
        if (!trackID.equals(track.trackID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subSystem.hashCode();
        result = 31 * result + occurrenceDate.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + trackID.hashCode();
        return result;
    }
}
