package com.example.tourist.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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

}
