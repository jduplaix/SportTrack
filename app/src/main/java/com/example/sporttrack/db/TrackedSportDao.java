package com.example.sporttrack.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TrackedSportDao {
    @Query("SELECT * FROM TrackedSport")
    List<TrackedSport> getAll();

    @Query("DELETE FROM TrackedSport")
    void deleteAll();

    @Insert
    void insert(TrackedSport trackedSport);

    @Update
    void update(TrackedSport trackedSport);

    @Delete
    void delete(TrackedSport trackedSport);
}
