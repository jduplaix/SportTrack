package com.example.sporttrack.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Sport.class, TrackedSport.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDb extends RoomDatabase {
    public abstract SportDao sportDao();
    public abstract TrackedSportDao trackedSportDao();
}
