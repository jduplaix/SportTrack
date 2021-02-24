package com.example.sporttrack.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "label", unique = true)})
public class Sport {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "trackLength")
    public boolean trackLength;

    @ColumnInfo(name = "trackTime")
    public boolean trackTime;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isTrackLength() {
        return trackLength;
    }

    public void setTrackLength(boolean trackLength) {
        this.trackLength = trackLength;
    }

    public boolean isTrackTime() {
        return trackTime;
    }

    public void setTrackTime(boolean trackTime) {
        this.trackTime = trackTime;
    }
}
