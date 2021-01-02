package com.example.tourist.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JourneyRepository {
    private JourneyDAO journeyDAO;
    private LiveData<List<Journey>> allJourneys;

    public JourneyRepository(Application application) {

    }
}
