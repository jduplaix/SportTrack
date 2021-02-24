package com.example.sporttrack.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sport.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract SportDao sportDao();
}
