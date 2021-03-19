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

        // récupération de l'id de l'objet trackedSport
        int histId = getItem(position).getId();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource,parent,false);

        //data binding
        TextView tvSport = convertView.findViewById(R.id.tvHistSport);
        tvSport.setText(String.valueOf(histId));

        return convertView;
    }


}
