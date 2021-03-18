package com.example.sporttrack.track;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackRealTimeActivity extends MyApplication {

    Sport sport;
    AppDb db;
    Date startDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_real_time);

        db = getDb();

        Intent incIntent = getIntent();
        sport = db.sportDao().getSport(incIntent.getStringExtra("spLabel"));

        // Personnalisation de l'écran en fonction du sport à suivre
        TextView tvSport = findViewById(R.id.tvTrackRTSport);
        tvSport.setText(sport.getLabel());

        // Instanciation du reste des composants qu'il faudra valoriser
        TextView tvStartDate = findViewById(R.id.tvTrackRTStartDate);
        TextView tvStartTime = findViewById(R.id.tvTrackRTStartTime);
        TextView tvEndDate = findViewById(R.id.tvTrackRTEndDate);
        TextView tvEndTime = findViewById(R.id.tvTrackRTEndTime);
        TextView tvTotalElapsed = findViewById(R.id.tvTrackRTTotalElapsed);
        Chronometer chrono = findViewById(R.id.chTrackRTChrono);
        Button btnStartStop = findViewById(R.id.btnTrackRTStart);

        // Instanciation du calendrier pour évaluer les dates/heures courantes + format
        SimpleDateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myTimeFormat = new SimpleDateFormat("HH:mm:ss");

        // Binding des boutons Démarrer/Arrêter & Annuler
        btnStartStop.setOnClickListener(v -> {
            if (btnStartStop.getText().equals("Démarrer")){
                startDateTime = Calendar.getInstance().getTime();
                tvStartDate.setText(myDateFormat.format(startDateTime));
                tvStartTime.setText(myTimeFormat.format(startDateTime));
                btnStartStop.setText("Arrêter");
                chrono.start();
            } else {
                chrono.stop();
                Date stopDateTime = Calendar.getInstance().getTime();
                tvEndDate.setText(myDateFormat.format(stopDateTime));
                tvEndTime.setText(myTimeFormat.format(stopDateTime));
                btnStartStop.setText("Démarrer");
            }
        });

        findViewById(R.id.btnTrackRTCancel).setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }
}