package com.example.sporttrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Clic bouton couleur : appel seekbars + récup et changement bg color
        findViewById(R.id.activite).setOnClickListener(v -> {
            View contentView = LayoutInflater.from(this).inflate(R.layout.activite_dialog, null);

            contentView.findViewById(R.id.demarrer_suivi).setOnClickListener(e -> {
                Toast.makeText(MainActivity.this,"vers activité démarrage suivi",Toast.LENGTH_SHORT).show();
            });

            contentView.findViewById(R.id.saisir_cr).setOnClickListener(e -> {
                Toast.makeText(MainActivity.this,"vers la saisie d'une activité passée",Toast.LENGTH_SHORT).show();
            });


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Un titre")
                    .setMessage("Un message")
                    .setView(contentView)
                    //.setPositiveButton("Valider",(d, which) -> {
                        //Toast.makeText(MainActivity.this,"vers activité démarrage suivi",Toast.LENGTH_SHORT).show();
                    //})
                    .setNegativeButton("Annuler", null)
                    .show();
        });



    }
}