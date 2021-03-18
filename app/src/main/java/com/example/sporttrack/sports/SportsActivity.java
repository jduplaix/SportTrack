package com.example.sporttrack.sports;


import android.content.Intent;
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
import com.example.sporttrack.track.TrackRealTimeActivity;

import java.util.ArrayList;

public class SportsActivity extends MyApplication {

    // $mode permet d'adapter l'activity en fonction du parcours UT :
    // 0 gestion sport,
    // 1 choix sport pour suivi temps réel
    // 2 choix sport pour CRA
    int mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        AppDb db = getDb();

        // Si l'on arrive sur la liste des sports via le parcours démarrage d'un suivi,
        // personnalisation du titre du layout + changement des débranchements onItemClick
        Intent incIntent = getIntent();
        mode = incIntent.getIntExtra("mode",0);
        if (mode > 0){
            TextView tvSportListTitle = findViewById(R.id.tvSportListTitle);
            tvSportListTitle.setText("Choisir un sport :");
        }
        Toast.makeText(this, String.valueOf(mode),Toast.LENGTH_SHORT).show();

        //instanciation lv de l'activity
        ListView lvSports = (ListView) findViewById(R.id.lvSports);

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
                String spLabel = tvSp.getText().toString();
                if (mode == 0){
                    Intent intent = new Intent(SportsActivity.this, SportsActivityForm.class);
                    intent.putExtra("spLabel", spLabel);
                    intent.putExtra("mode", mode);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SportsActivity.this, TrackRealTimeActivity.class);
                    intent.putExtra("spLabel", spLabel);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // accès au form de gestion d'un sport (création ou modif si label déjà utilisé)
        findViewById(R.id.fabAddSport).setOnClickListener(v -> {
            Intent intent = new Intent(SportsActivity.this, SportsActivityForm.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
            finish();
        });
    }
}
