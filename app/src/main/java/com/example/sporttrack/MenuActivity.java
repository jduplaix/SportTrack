package com.example.sporttrack;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sporttrack.history.HistoryActivity;
import com.example.sporttrack.sports.SportsActivity;

public class MenuActivity extends MyApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.activity).setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_menu_track_dialog, null);

            dialogView.findViewById(R.id.track).setOnClickListener(e -> {
                startTrackingActivity(1);
            });

            dialogView.findViewById(R.id.report).setOnClickListener(e -> {
                startTrackingActivity(2);
            });

            new AlertDialog.Builder(MenuActivity.this)
                    .setTitle("Suivre une activité :")
                    .setView(dialogView)
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        //Appel de l'activité historique des activités sportives
        findViewById(R.id.history).setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        //Appel de l'activité de gestion des sports
        findViewById(R.id.sports).setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, SportsActivity.class);
            startActivity(intent);
        });

        //Fermeture complète de l'app sur clic bouton QUITTER
        findViewById(R.id.exit).setOnClickListener(v -> {
            finishAndRemoveTask();
        });
    }

    protected void startTrackingActivity(int mode){
        Intent intent = new Intent(MenuActivity.this, SportsActivity.class);
        intent.putExtra("mode",mode);
        startActivity(intent);
    }
}