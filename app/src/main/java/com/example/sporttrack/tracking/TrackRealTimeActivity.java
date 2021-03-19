package com.example.sporttrack.tracking;



import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;
import com.example.sporttrack.db.TrackedSport;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackRealTimeActivity extends MyApplication {

    Sport sport;
    TrackedSport trackedSport;
    AppDb db;
    Date startDateTime;
    Date stopDateTime;

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
            switch((String) btnStartStop.getText()){
                case "Démarrer":
                    trackedSport = new TrackedSport(sport.getLabel(), Calendar.getInstance().getTime());
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
                    //startDateTime = Calendar.getInstance().getTime();
                    tvStartDate.setText(myDateFormat.format(trackedSport.getTrackingStart()));
                    tvStartTime.setText(myTimeFormat.format(trackedSport.getTrackingStart()));
                    btnStartStop.setText("Arrêter");
                    break;
                case "Arrêter":
                    chrono.stop();
                    trackedSport.setTrackingEnd(Calendar.getInstance().getTime());
                    tvEndDate.setText(myDateFormat.format(trackedSport.getTrackingEnd()));
                    tvEndTime.setText(myTimeFormat.format(trackedSport.getTrackingEnd()));
                    btnStartStop.setText("Enregistrer");

                    long difference = Math.abs(trackedSport.getTrackingEnd().getTime() - trackedSport.getTrackingStart().getTime());
                    tvTotalElapsed.setText(elapsedTime(difference));
                    break;
                case "Enregistrer":
                    db.trackedSportDao().insert(trackedSport);
                    Toast.makeText(this,"Suivi du " + sport.getLabel() + " enregistré.",Toast.LENGTH_SHORT).show();
                    finish();
            }
        });

        findViewById(R.id.btnTrackRTCancel).setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    protected String elapsedTime(long dateTimeDiff){
        long remainingTime;
        trackedSport.setElapsedDays((int) dateTimeDiff /(24 * 60 * 60 * 1000));
        remainingTime = dateTimeDiff % (24 * 60 * 60 * 1000);
        trackedSport.setElapsedHours((int) remainingTime / (60 * 60 * 1000));
        remainingTime = remainingTime % (60 * 60 * 1000);
        trackedSport.setElapsedMinutes((int) remainingTime / (60 * 1000));
        remainingTime = remainingTime % ( 60 * 1000);
        trackedSport.setElapsedSeconds((int) remainingTime / 1000);
        return trackedSport.getElapsedDays() + "j - "
                + trackedSport.getElapsedHours() + "h - "
                + trackedSport.getElapsedMinutes() + "m - "
                + trackedSport.getElapsedSeconds() + "s";
    }
}