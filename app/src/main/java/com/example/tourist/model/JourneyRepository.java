package com.example.tourist.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

public class JourneyRepository {
    private JourneyDAO journeyDAO;
    private List<JourneyWithMoments> allJourneys;

    public JourneyRepository(Application application) {
        TouristRoomDatabase db = TouristRoomDatabase.getDatabase(application);
        journeyDAO = db.journeyDAO();
        allJourneys = journeyDAO.getJourneyWithMoments();
    }

    public List<JourneyWithMoments> getAllJourneys() { return this.allJourneys;}

    public void insert(Journey journey) {
        new JourneyRepository.insertAsyncTask(this.journeyDAO).execute(journey);
    }

    private static class insertAsyncTask extends AsyncTask<Journey, Void, Void> {
        private WeakReference<JourneyDAO> journeyRef;

        public insertAsyncTask(JourneyDAO dao) {
            this.journeyRef = new WeakReference<>(dao);
        }

        @Override
        protected Void doInBackground(Journey... journeys) {
            JourneyDAO dao = journeyRef.get();
            dao.insert(journeys[0]);
            return null;
        }
    }
}
