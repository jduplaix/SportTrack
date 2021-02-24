package com.example.sporttrack;


import android.os.Bundle;

import androidx.annotation.Nullable;

public class SportsActivity extends MyApplication{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
    }
}
