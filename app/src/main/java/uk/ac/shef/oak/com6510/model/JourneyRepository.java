package uk.ac.shef.oak.com6510.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Represents the Repository of the Journey entity.
 * This class will be used by the Journey View Model to access the data of a journey
 */
public class JourneyRepository {
    private JourneyDAO journeyDAO;
    private LiveData<List<JourneyWithMoments>> allJourneys;

    /**
     * Class constructor that gets a handle of the database and initialises Journey DAO
     *
     * @param application the application state
     */
    public JourneyRepository(Application application) {
        TouristRoomDatabase db = TouristRoomDatabase.getDatabase(application);
        journeyDAO = db.journeyDAO();
        allJourneys = journeyDAO.getJourneyWithMoments();
    }

    /**
     * Gets all JourneyWithMoments rows from the database
     *
     * @return all of JourneyWithMoments instances
     */
    public LiveData<List<JourneyWithMoments>> getAllJourneys() { return this.allJourneys;}

    /**
     * Asynchronously inserts a Journey into the database
     *
     * @param journey the journey to be inserted
     */
    public void insert(Journey journey) {
        new JourneyRepository.insertAsyncTask(this.journeyDAO).execute(journey);
    }

    /**
     * This class will handle inserting a Journey into the database asynchronously
     * in the background
     */
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
