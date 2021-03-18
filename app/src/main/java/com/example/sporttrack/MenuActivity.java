package com.example.sporttrack;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.sporttrack.sports.SportsActivity;

public class MenuActivity extends MyApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.activity).setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_menu_session_dialog, null);

            dialogView.findViewById(R.id.track).setOnClickListener(e -> {
                Toast.makeText(MenuActivity.this,"vers activité démarrage suivi",Toast.LENGTH_SHORT).show();
            });

            dialogView.findViewById(R.id.report).setOnClickListener(e -> {
                Toast.makeText(MenuActivity.this,"vers la saisie d'une activité passée",Toast.LENGTH_SHORT).show();
            });


            new AlertDialog.Builder(MenuActivity.this)
                    .setTitle("Suivre une activité :")
                    .setView(dialogView)
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        //Fermeture complète de l'app sur clic bouton QUITTER
        findViewById(R.id.exit).setOnClickListener(v -> {
            finishAndRemoveTask();
        });

        //Appel de l'activité de gestion des sports
        findViewById(R.id.sports).setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, SportsActivity.class);
            startActivity(intent);
        });
    }
}