package com.example.tourist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tourist.model.Journey;
import com.example.tourist.model.JourneyRepository;
import com.example.tourist.model.JourneyWithMoments;

import java.util.List;

/**
 * Represents the View Model of the Journey entity
 */
public class JourneyViewModel extends AndroidViewModel {
    private JourneyRepository mRepository;
    private LiveData<List<JourneyWithMoments>> mAllJourneys;

    /**
     * Constructor of the class which initialises the Journey repository
     *
     * @param application the application state
     */
    public JourneyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new JourneyRepository(application);
        mAllJourneys = mRepository.getAllJourneys();
    }

    public LiveData<List<JourneyWithMoments>> getAllJourneys() {
        return this.mAllJourneys;
    }

    public void insert(Journey journey) {
        this.mRepository.insert(journey);
    }
}
