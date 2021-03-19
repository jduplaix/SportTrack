package com.example.sporttrack.sports;

import androidx.appcompat.widget.SwitchCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sporttrack.MyApplication;
import com.example.sporttrack.R;
import com.example.sporttrack.db.AppDb;
import com.example.sporttrack.db.Sport;
import com.google.android.material.snackbar.Snackbar;


public class SportsActivityForm extends MyApplication {

    AppDb db;
    Sport sport; //sport géré dans le form
    Sport incSport; // label du sport arrivant de la liste exposée sur ActivitySport
    int mode; // voir SportsActivity.class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_form);

        db = getDb();

        // recup parcours utilisateur
        Intent incIntent = getIntent();
        mode = incIntent.getIntExtra("mode",0);

        // si le form est accédé depuis un clic sur item de la liste,
        // récup du libellé du sport > instanciation du sport correspondant à l'item
        //Intent incIntent = getIntent();
        incSport = db.sportDao().getSport(incIntent.getStringExtra("spLabel"));

        if (incSport != null) {
            // changement de libellé du bouton enregistrer > modifier
            Button saveBtn = findViewById(R.id.saveSport);
            saveBtn.setText("Modifier");
            // valorisation du form avec les attr du sp transmis
            EditText etSpLabel = findViewById(R.id.sportLabel);
            etSpLabel.setText(incSport.getLabel());
            SwitchCompat swSportLength = (SwitchCompat) findViewById(R.id.sportLength);
            swSportLength.setChecked(incSport.getTrackLength() == 0 ? false : true);
            SwitchCompat swSportTime = (SwitchCompat) findViewById(R.id.sportTime);
            swSportTime.setChecked(incSport.getTrackTime() == 0 ? false : true);
        }

        //Enregistrement du sport renseigné sur le formulaire de l'activité
        findViewById(R.id.saveSport).setOnClickListener(v -> {
            sportSave();
        });

        //Suppression du sport
        findViewById(R.id.deleteSport).setOnClickListener(v -> {
            EditText etSpLabel = findViewById(R.id.sportLabel);
            sport = db.sportDao().getSport(etSpLabel.getText().toString());
            if (sport == null){
                Toast.makeText(this,
                        "Le sport \""+ etSpLabel.getText().toString() + "\" n'existe pas."
                        , Toast.LENGTH_SHORT).show();
            } else {
                deleteSport();
            }
        });

        // retour à la liste en cliquant sur Annuler = idem onBackPressed
        findViewById(R.id.cancelSportForm).setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        // l'activity list est fermée; réouverture si back pressed
        Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
        finish();
    }

    protected void sportSave(){
        /*
        * Déclenche les contrôles et l'enregistrement de la saisie form
        */
        //récupération des valeurs du form
        //libellé du sport
        EditText etSpLabel = findViewById(R.id.sportLabel);
        // suivi distance (conversion bool vers int)
        SwitchCompat swSportLength = (SwitchCompat) findViewById(R.id.sportLength);
        int length = swSportLength.isChecked() ? 1 : 0;
        // suivi durée (conversion bool vers int)
        SwitchCompat swSportTime = (SwitchCompat) findViewById(R.id.sportTime);
        int time = swSportTime.isChecked() ? 1 : 0;
        //hydratation d'un nouveau sport avec la saisie du form
        sport  = new Sport(etSpLabel.getText().toString(),length,time);
        // Contrôle de saisie
        String check = integrityCheck();
        Sport checkDup = duplicateCheck();
        if (check.isEmpty()) {
            View snackAnchor = findViewById(R.id.saveSport); // décla anticipée de l'ancre du snack en cas de notif
            // si modif sport de la liste
            if (incSport != null) {
                // si MaJ du label avec un label d'un autre sport existant : refus > l'UT doit en supprimer un lui même
                if (checkDup != null && !checkDup.getLabel().equals(incSport.getLabel())){
                    // Mise à jour impossible : le sport "" existe déjà
                    Snackbar snackUpgradeKo = Snackbar
                            .make(snackAnchor, "Mise à jour impossible:\nLe sport \"" + sport.getLabel() + "\" existe déjà.", Snackbar.LENGTH_SHORT);
                    snackUpgradeKo.setAnchorView(snackAnchor).show();
                } else {
                    upgradeSport();
                }
            } else {
                // sinon si nouveau sport verif si sportLabel existe déjà avant insert
                if (checkDup == null){
                    insertSport();
                } else {
                    // s'il existe déjà alors qu'on veut le créer, snack proposition update de l'existant en base
                    Snackbar snackUpdate = Snackbar
                            .make(snackAnchor, "Le sport \"" + sport.getLabel() + "\" existe déjà.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Modifier", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateSport();
                                }
                            });
                    snackUpdate.setAnchorView(snackAnchor).show();
                }
            }
        }
        else {
            // Toast si ctrl de saisie KO
            Toast.makeText(this, check, Toast.LENGTH_LONG).show();
        }
    }

    protected String integrityCheck(){
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

        if (sport.getLabel().contains(newLine)){
            checkResult = "Erreur de saisie :\n" +
                    "Le libellé du sport ne doit pas contenir de saut de ligne.";
        }
        if (sport.getLabel().isEmpty()){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe libellé du sport est obligatoire.";
        }
        if (sport.getLabel().length() >= 80){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe libellé du sport est trop long (80 caractères maximum.";
        }
        if (sport.getTrackTime() + sport.getTrackLength() == 0){
            checkResult = checkResult == "" ? "Erreur de saisie :" : checkResult;
            checkResult = checkResult + "\nLe sport doit au moins pouvoir être suivi sur la distance ou la durée.";
        }
        return checkResult;
    }

    protected Sport duplicateCheck(){
        // vérification doublon de sport
        // recherche dans la table sport si le label existe déjà, si oui, retourne le Sport correspondant
        Sport check = db.sportDao().getSport(sport.getLabel());
        return check;
    }

    protected void insertSport(){
        db.sportDao().insert(sport);
        Toast.makeText(this,
                "Le sport \""+ sport.getLabel() + "\" a bien été enregistré."
                , Toast.LENGTH_SHORT).show();
        //rechargement de l'activité sportList pour maj la listview (easy mode)
        Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
        //fermeture de l'activité form
        finish();
    }

    protected void updateSport(){
        try {
            db.sportDao().updateSport(sport);
            Toast.makeText(this,"Le sport \"" + sport.getLabel() + "\" a bien été modifié.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
            finish();
        } catch (Exception e){
            Toast.makeText(this,"Une erreur est survenue : " +e,Toast.LENGTH_SHORT).show();
        }
    }

    protected void deleteSport(){
        //snack confirmation avant suppression
        View snackAnchor = findViewById(R.id.saveSport);
        Snackbar snackDelete = Snackbar
                .make(snackAnchor, "Le sport \""+ sport.getLabel() + "\" va être supprimé.",Snackbar.LENGTH_INDEFINITE)
                .setAction("Confirmer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.sportDao().delete(sport);
                        Toast.makeText(SportsActivityForm.this,"Le sport \"" + sport.getLabel() + "\" a bien été supprimé.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
                        intent.putExtra("mode", mode);
                        startActivity(intent);
                        finish();
                    }
                });
        snackDelete.setAnchorView(snackAnchor).show();
    }

    protected void upgradeSport(){
        db.sportDao().upgradeSport(incSport.getLabel(), sport.getLabel(),sport.getTrackLength(),sport.getTrackTime());
        Toast.makeText(this,"Le sport \"" + incSport.getLabel() + "\" a bien été mis à jour.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
        finish();
    }
}