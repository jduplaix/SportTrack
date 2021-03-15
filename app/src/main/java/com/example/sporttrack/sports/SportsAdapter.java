package com.example.sporttrack.sports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sporttrack.R;
import com.example.sporttrack.db.Sport;

import java.util.ArrayList;

public class SportsAdapter extends ArrayAdapter<Sport> {

    private Context myContext;
    int myResource;

    public SportsAdapter(Context context, int resource, ArrayList<Sport> objects){
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String label  = getItem(position).getLabel();
        int time = getItem(position).getTrackTime();
        int length = getItem(position).getTrackLength();
        String type;
        if (time == 1 & length == 1){
            type = "durée | distance";
        } else if (time == 1){
            type = "durée";
        } else {
            type = "distance";
        }

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView tvLabel = (TextView) convertView.findViewById(R.id.tvSportLabel);
        TextView tvType = (TextView) convertView.findViewById(R.id.tvSportType);
        tvLabel.setText(label);
        tvType.setText(type);

        return convertView;
    }
}
