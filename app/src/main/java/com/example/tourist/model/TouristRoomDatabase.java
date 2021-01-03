package com.example.tourist.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.Objects;

@Database(entities = {Moment.class, Journey.class}, version = 1, exportSchema = false)
public abstract class TouristRoomDatabase extends RoomDatabase {
    public abstract MomentDAO momentDAO();
    public abstract JourneyDAO journeyDAO();
    private static TouristRoomDatabase INSTANCE;

    public static TouristRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TouristRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TouristRoomDatabase.class, "tourist_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Callback to populate the database
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    /**
     * Populate the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MomentDAO momentDAO;
        private final JourneyDAO journeyDAO;

        PopulateDbAsync(TouristRoomDatabase db) {
            momentDAO = db.momentDAO();
            journeyDAO = db.journeyDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // create a new journey
            Journey journey = new Journey();
            Log.d("JourneyID", Integer.toString(journey.getId()));
            journey.setTitle("My first journey");
            journey.setStartDate("20/12/2020");
            journey.setEndDate("10/01/2021");
            journeyDAO.insert(journey);

            Journey journeyFromDatabase = journeyDAO.getLastJourney();
            Log.d("JourneyFromDatabaseID", Integer.toString(journeyFromDatabase.getId()));

            // get moments from the database
            for (Moment moment : momentDAO.getNotLiveMoments()) {
                // assign each moment the id of the journey created above
                moment.setJourneyId(journeyFromDatabase.getId());
                Log.d("SettingJourneyID", Long.toString(moment.getJourneyId()));
                momentDAO.updateMoment(moment);
            }


            return null;
        }
    }
}
