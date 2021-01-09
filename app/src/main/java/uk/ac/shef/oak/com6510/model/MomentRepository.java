package uk.ac.shef.oak.com6510.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Represents the Repository of the Moment entity.
 * This class will be used by the Moment View Model to access the data of a moment
 */
public class MomentRepository {
    private MomentDAO momentDAO;
    private LiveData<List<Moment>> allMoments;

    /**
     * Class constructor that gets a handle of the database and initialises Moment DAO
     *
     * @param application the application state
     */
    public MomentRepository(Application application, int journeyId) {
        TouristRoomDatabase db = TouristRoomDatabase.getDatabase(application);
        momentDAO = db.momentDAO();
        allMoments = momentDAO.getAllJourneyMoments(journeyId);
    }

    /**
     * Gets all Moment rows from the database
     *
     * @return all of Moment instances
     */
    public LiveData<List<Moment>> getAllMoments() {
        return this.allMoments;
    }


    /**
     * Asynchronously inserts a Moment into the database
     *
     * @param moment the moment to be inserted
     */
    public void insert(Moment moment) {
        new insertAsyncTask(this.momentDAO).execute(moment);
    }

    /**
     * This class will handle inserting a Moment into the database asynchronously
     * in the background
     */
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