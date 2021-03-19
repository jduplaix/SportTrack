package com.example.sporttrack.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sporttrack.R;
import com.example.sporttrack.db.TrackedSport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<TrackedSport> {

    private Context myContext;
    int myResource;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TrackedSport> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // récupération de l'objet trackedSport
        TrackedSport track = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource,parent,false);

        // instanciation des composants du layout adapter pour binder les data du suivi
        TextView tvSport = convertView.findViewById(R.id.tvHistSport);
        TextView tvStart = convertView.findViewById(R.id.tvHistStart);
        TextView tvTime = convertView.findViewById(R.id.tvHistTime);
        TextView tvDist = convertView.findViewById(R.id.tvHistDist);

        // Définition d'un Date formater
        SimpleDateFormat myDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Binding des attr de l'objet trackedTime avec chaque TextView correspondant
        tvSport.setText(track.getTrackedSport());
        tvStart.setText(tvStart.getText() + myDateTimeFormat.format(track.getTrackingStart()));

        // Contournement de l'absence d'objet sport pour vérifier ce qui doit être suivi pour le sport
        // Si pas de durée enregistrée, je considère qu'elle n'est pas suivie pour ce sport
        // idem pour la distance
        if (track.getElapsedDays() == 0
                && track.getElapsedHours() ==0
                && track.getElapsedMinutes() == 0
                && track.getElapsedSeconds() == 0) {
            tvTime.setVisibility(TextView.GONE);
        } else {
            tvTime.setText(track.getElapsedDays() + "j, "
                    + track.getElapsedHours() + "h"
                    + track.getElapsedMinutes() + "m"
                    + track.getElapsedSeconds() + "s"
            );
        }

        if (track.getTrackedDistance() == 0){
            tvDist.setVisibility(TextView.GONE);
        } else {
            tvDist.setText(track.getTrackedDistance() + "km");
        }

        return convertView;
    }


}
