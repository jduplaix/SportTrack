package com.example.sporttrack.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SportDao {
    @Query("SELECT * FROM Sport")
    List<Sport> getAll();

    @Query("SELECT * FROM Sport WHERE label = :sportLabel")
    public Sport getSport(String sportLabel);

    @Query("DELETE FROM Sport")
    void deleteAll();

    @Update
    void updateSport(Sport sport);

    @Insert
    long insert(Sport sport);

    @Delete
    void delete(Sport sport);
}
