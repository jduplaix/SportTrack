package com.example.sporttrack.sports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sporttrack.R;
import com.example.sporttrack.db.Sport;

import java.util.ArrayList;

public class SportsAdapter extends ArrayAdapter<Sport> {
    public SportsAdapter(Context context, ArrayList<Sport> sports){
        super(context, 0,sports);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sport sport = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_sports, parent, false);
        }
        // Lookup view for data population
        TextView sportsItem = (TextView) convertView.findViewById(R.id.lire);
        // Populate the data into the template view using the data object
        sportsItem.setText(sport.getLabel());
        // Return the completed view to render on screen
        return convertView;
    }
}
