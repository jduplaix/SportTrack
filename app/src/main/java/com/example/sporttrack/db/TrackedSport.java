package com.example.sporttrack.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class TrackedSport {

    // Constructeur
    public TrackedSport(@NonNull String trackedSport, @NonNull Date trackingStart) {
        this.trackedSport = trackedSport;
        this.trackingStart = trackingStart;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "trackedSport")
    public String trackedSport;

    @NonNull
    @ColumnInfo(name = "trackingStart")
    public Date trackingStart;

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
    public String getTrackedSport() {
        return trackedSport;
    }

    @NonNull
    public Date getTrackingStart() {
        return trackingStart;
    }

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

    public void setTrackedSport(@NonNull String trackedSport) {
        this.trackedSport = trackedSport;
    }

    public void setTrackingStart(@NonNull Date trackingStart) {
        this.trackingStart = trackingStart;
    }

    public void setTrackingEnd(Date trackingEnd) {
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
