package com.example.sporttrack.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity//supp temporaire de la contrainte d'unicité(indices = {@Index(value = "label", unique = true)})
public class Sport {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "trackLength")
    public int trackLength;

    @ColumnInfo(name = "trackTime")
    public int trackTime;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public int getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(int trackTime) {
        this.trackTime = trackTime;
    }
}
