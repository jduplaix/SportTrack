package com.example.sporttrack.sports;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;

import java.util.ArrayList;

public class SportsActivity extends MyApplication {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        AppDb db = getDb();
        //instanciation lv de l'activity
        ListView lvSports = (ListView) findViewById(R.id.lvSports);


        //Mock purge table Sports + création de 20 sports en base
        db.sportDao().deleteAll();
        for (int i=1; i<21; i++){
            Sport sp = new Sport();
            sp.label = "sport"+i;
            sp.trackLength = i % 3 == 0 || i %2 == 0 ? 1 : 0;
            sp.trackTime = i % 3 == 0 || i %2 != 0 ? 1 : 0;
            db.sportDao().insert(sp);
        }

        //Instanciation d'un tableau de sports + alim avec les sports en base
        ArrayList<Sport> sportsList = new ArrayList<>();
        sportsList = (ArrayList<Sport>) db.sportDao().getAll();

        //instanciation adapter perso avec collection sports + layout des items
        SportsAdapter adapter = new SportsAdapter(this, R.layout.activity_sports_adapter, sportsList);
        // Binding de l'adapter perso à la listview de l'activity sports
        lvSports.setAdapter(adapter);
        lvSports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvSp = (TextView) view.findViewById(R.id.tvSportLabel);
                String sp = tvSp.getText().toString();
                Toast.makeText(SportsActivity.this, "Débranche vers le menu modifier du sport " + sp, Toast.LENGTH_SHORT).show();
            }
        });

        //MOCK : écriture d'un sport en base
        findViewById(R.id.fabCreateSport).setOnClickListener(v -> {
            Toast.makeText(this, "Débranche vers l'activité création sport",Toast.LENGTH_SHORT).show();
        });

        //BUG : On back pressed : revient au menu du tel
    }
}
