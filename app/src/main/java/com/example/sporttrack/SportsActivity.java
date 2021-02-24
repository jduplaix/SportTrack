package com.example.sporttrack;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;

public class SportsActivity extends MyApplication {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        AppDb db = getDb();

        //MOCK : test écriture d'un sport en base
        findViewById(R.id.fab).setOnClickListener(v -> {
            Sport sp = new Sport();
            sp.label = "foot";
            sp.trackLength = false;
            sp.trackTime = true;
            db.sportDao().insert(sp);
        });

        /* WIP : test lecture d'un sport en base
        findViewById(R.id.lire).setOnClickListener(v -> {

        });*/

        //BUG : On back pressed : revient au menu du tel
    }
}
