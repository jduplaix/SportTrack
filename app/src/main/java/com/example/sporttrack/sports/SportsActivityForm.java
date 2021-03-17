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
    Sport incSport; // sport issu de la liste exposée sur ActivitySport

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_form);

        db = getDb();

        // si le form est accédé depuis un clic sur item de la liste, récup du libellé > instanciation du sp de l'item
        Intent incIntent = getIntent();
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

        //Suppression du sport identifié dans le formulaire
        findViewById(R.id.deleteSport).setOnClickListener(v -> {
            EditText etSpLabel = findViewById(R.id.sportLabel);
            Sport sp = db.sportDao().getSport(etSpLabel.getText().toString());
            if (sp == null){
                Toast.makeText(this,
                        "Le sport \""+ etSpLabel.getText().toString() + "\" n'existe pas."
                        , Toast.LENGTH_SHORT).show();
            } else {
                deleteSport(sp);
            }
        });

        // fermeture de l'activité en cliquant sur Annuler = idem onBackPressed
        findViewById(R.id.cancelSportForm).setOnClickListener(v -> {
            finish();
        });
    }

    protected void sportSave(){
        /*
        * Déclenche les contrôles et l'enregistrement de la saisie
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
        Sport sp  = new Sport(etSpLabel.getText().toString(),length,time);
        // Contrôle de saisie
        String check = integrityCheck(sp);
        if (check.isEmpty()) {
            // si modif sport de la liste
            if (incSport != null) {
                sport = sp;
                upgradeSport();
            } else {
                // sinon verif si sportLabel existe déjà avant insert
                Sport checkDup = duplicateCheck(sp);
                if (checkDup == null){
                    insertSport(sp);
                } else {
                    // si le sport existe déjà alors qu'on veut le créer, snack proposition modif de l'existant en base
                    View snackAnchor = findViewById(R.id.saveSport);
                    Snackbar snackUpdate = Snackbar
                            .make(snackAnchor, "Le sport \"" + sp.getLabel() + "\" existe déjà.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Modifier", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateSport(sp);
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

    protected Sport duplicateCheck(Sport sport){
        // vérification doublon de sport
        // si le sport existe déjà, proposition écrasement avec les données du form actuel
        Sport check = db.sportDao().getSport(sport.getLabel());
        return check;
    }

    protected void insertSport(Sport sport){
        db.sportDao().insert(sport);
        Toast.makeText(this,
                "Le sport \""+ sport.getLabel() + "\" a bien été enregistré."
                , Toast.LENGTH_SHORT).show();
        //rechargement de l'activité sportList pour maj la listview (easy mode)
        Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
        startActivity(intent);
        //fermeture de l'activité form
        finish();
    }

    protected void updateSport(Sport sport){
        try {
            db.sportDao().updateSport(sport);
            Toast.makeText(this,"Le sport \"" + sport.getLabel() + "\" a bien été modifié.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SportsActivityForm.this, SportsActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e){
            Toast.makeText(this,"Une erreur est survenue : " +e,Toast.LENGTH_SHORT).show();
        }
    }

    protected void deleteSport(Sport sport){
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
        startActivity(intent);
        finish();
    }
}