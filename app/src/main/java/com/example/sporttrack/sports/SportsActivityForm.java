package com.example.sporttrack.sports;

import androidx.appcompat.widget.SwitchCompat;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;

public class SportsActivityForm extends MyApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_form);

        AppDb db = getDb();

        //Enregistrement du sport renseigné sur le formulairede l'activité
        findViewById(R.id.saveSport).setOnClickListener(v -> {
            //récupération des valeurs du form
            //libellé du sport
            EditText etSpLabel = findViewById(R.id.sportLabel);
            // suivi distance (conversion bool vers int)
            SwitchCompat swSportLength = (SwitchCompat) findViewById(R.id.sportLength);
            int length = swSportLength.isChecked() ? 1 : 0;
            //suivi durée (conversion bool vers int)
            SwitchCompat swSportTime = (SwitchCompat) findViewById(R.id.sportTime);
            int time = swSportTime.isChecked() ? 1 : 0;
            //hydratation d'un nouveau sport avec la saisie du form
            Sport sp  = new Sport();
            sp.setLabel(etSpLabel.getText().toString());
            sp.setTrackLength(length);
            sp.setTrackTime(time);
            // INSERT dans la table
            String check = integrityCheck(sp);
            if (check == ""){
                db.sportDao().insert(sp);
                //rechargement de l'activité sportList pour maj la listview (easy mode) et fermeture de l'activité form
                Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, check, Toast.LENGTH_LONG).show();
            }
        });

        // fermeture de l'activité en cliquant sur Annuler = idem onBackPressed
        findViewById(R.id.cancelSportForm).setOnClickListener(v -> {
            finish();
        });
    }

    protected String integrityCheck(Sport sport){
        /* 4 tests menés :
         - le label ne doit pas contenir de saut de ligne
         - le label ne doit pas être vide
         - le label doit faire moins de 80 caractères
         - le sport doit au moins êtrre mesurable par la durée OU la distance
        */
        String checkResult = "";
        String label = sport.getLabel();
        int time = sport.getTrackTime();
        int length = sport.getTrackLength();
        String newLine = System.getProperty("line.separator");

        if (label.contains(newLine)){
            checkResult = "Erreur de saisie :\n" +
                    "Le libellé du sport ne doit pas contenir de saut de ligne.";
        }
        if (label.isEmpty()){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe libellé du sport est obligatoire.";
        }
        if (label.length() >= 80){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe libellé du sport est trop long (80 caractères maximum.";
        }
        if (time + length == 0){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe sport doit au moins pouvoir être suivi sur la distance ou la durée.";
        }
        return checkResult;
    }


}