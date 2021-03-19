package com.example.sporttrack.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class TrackedSport {

    // Constructeur
    public TrackedSport(@NonNull Sport trackedSport, @NonNull Date trackingStart, @NonNull Date trackingEnd) {
        this.trackedSport = trackedSport;
        this.trackingStart = trackingStart;
        this.trackingEnd = trackingEnd;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "trackedSport")
    public Sport trackedSport;

    @NonNull
    @ColumnInfo(name = "trackingStart")
    public Date trackingStart;

    @NonNull
    @ColumnInfo(name = "trackingEnd")
    public Date trackingEnd;

    @ColumnInfo(name = "elapsedDays")
    public int elapsedDays;

    @ColumnInfo(name = "elapsedHours")
    public int elapsedHours;

    @ColumnInfo(name = "elapsedMinutes")
    public int elapsedMinutes;

    @ColumnInfo(name = "elapsedSeconds")
    public int elapsedSeconds;

    @ColumnInfo(name = "trackedDistance")
    public int trackedDistance;

    // Getters
    public int getId() {
        return id;
    }

    @NonNull
    public Sport getTrackedSport() {
        return trackedSport;
    }

    @NonNull
    public Date getTrackingStart() {
        return trackingStart;
    }

    @NonNull
    public Date getTrackingEnd() {
        return trackingEnd;
    }

    public int getElapsedDays() {
        return elapsedDays;
    }

    public int getElapsedHours() {
        return elapsedHours;
    }

    public int getElapsedMinutes() {
        return elapsedMinutes;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public int getTrackedDistance() {
        return trackedDistance;
    }

    // Setters

    public void setTrackedSport(@NonNull Sport trackedSport) {
        this.trackedSport = trackedSport;
    }

    public void setTrackingStart(@NonNull Date trackingStart) {
        this.trackingStart = trackingStart;
    }

    public void setTrackingEnd(@NonNull Date trackingEnd) {
        this.trackingEnd = trackingEnd;
    }

    public void setElapsedDays(int elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public void setElapsedHours(int elapsedHours) {
        this.elapsedHours = elapsedHours;
    }

    public void setElapsedMinutes(int elapsedMinutes) {
        this.elapsedMinutes = elapsedMinutes;
    }

    public void setElapsedSeconds(int elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public void setTrackedDistance(int trackedDistance) {
        this.trackedDistance = trackedDistance;
    }
}
