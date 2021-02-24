package com.example.sporttrack;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sporttrack.db.AppDb;

public abstract class MyApplication extends AppCompatActivity {

    private AppDb db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDb.class,"database-name").allowMainThreadQueries().build();
    }

    public AppDb getDb(){
        return db;
    }
}
