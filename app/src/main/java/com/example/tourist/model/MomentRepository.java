package com.example.tourist.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

public class MomentRepository {
    private MomentDAO momentDAO;
    private LiveData<List<Moment>> allMoments;

    public MomentRepository(Application application, int journeyId) {
        TouristRoomDatabase db = TouristRoomDatabase.getDatabase(application);
        momentDAO = db.momentDAO();
        allMoments = momentDAO.getAllJourneyMoments(journeyId);
    }

    public LiveData<List<Moment>> getAllMoments() {
        return this.allMoments;
    }


    public void insert(Moment moment) {
        new insertAsyncTask(this.momentDAO).execute(moment);
    }

    private static class insertAsyncTask extends AsyncTask<Moment, Void, Void> {
        private WeakReference<MomentDAO> daoRef;

        public insertAsyncTask(MomentDAO dao) {
            this.daoRef = new WeakReference<>(dao);
        }

        @Override
        protected Void doInBackground(Moment... moments) {
            MomentDAO dao = daoRef.get();
            dao.insert(moments[0]);
            return null;
        }
    }
}