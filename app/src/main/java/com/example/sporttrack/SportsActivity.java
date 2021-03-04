package com.example.sporttrack;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportsActivity extends MyApplication {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        AppDb db = getDb();

        //MOCK : test écriture d'un sport en base
        findViewById(R.id.fab).setOnClickListener(v -> {
            Sport sp = new Sport();
            sp.label = "tennis";
            sp.trackLength = 0;
            sp.trackTime = 1;
            db.sportDao().insert(sp);
        });

        /* WIP : test lecture d'un sport en base */
        findViewById(R.id.lire).setOnClickListener(v -> {
            ArrayList<Sport> sports = (ArrayList<Sport>) db.sportDao().getAll();
            SportsAdapter adapter = new SportsAdapter(this, sports);
            ListView listView = (ListView) findViewById(R.id.sports);
            listView.setAdapter(adapter);
        });

        //BUG : On back pressed : revient au menu du tel
    }
}
