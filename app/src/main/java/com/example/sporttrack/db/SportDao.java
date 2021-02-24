package com.example.sporttrack.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SportDao {
    @Query("SELECT * FROM Sport")
    List<Sport> getAll();

    @Insert
    long insert(Sport sport);

    @Delete
    void delete(Sport sport);
}
