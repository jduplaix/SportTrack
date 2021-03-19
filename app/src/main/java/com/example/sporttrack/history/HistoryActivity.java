package com.example.sporttrack.history;

import android.os.Bundle;
import android.widget.ListView;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.TrackedSport;

import java.util.ArrayList;

public class HistoryActivity extends MyApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        AppDb db = getDb();

        // Instanciation ListView du layout
        ListView lvHist = findViewById(R.id.lvHist);

        //Instanciation tableau de suivis
        ArrayList<TrackedSport> history = new ArrayList<>();
        history = (ArrayList<TrackedSport>) db.trackedSportDao().getAll();

        // Instanciation Adapter perso avec collec suivis + layout des items de la liste
        HistoryAdapter histAdapter = new HistoryAdapter(this,R.layout.activity_history_adapter,history);
        //set de l'adapter sur le layout de la liste
        lvHist.setAdapter(histAdapter);
    }
}