package com.example.tourist.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tourist.R;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Moment.class}, version = 1, exportSchema = false)
public abstract class MomentRoomDatabase extends RoomDatabase {
    public abstract MomentDAO momentDAO();
    private static MomentRoomDatabase INSTANCE;

    public static MomentRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MomentRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MomentRoomDatabase.class, "moment_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private MomentDAO mDao;
        List<Moment> moments = new ArrayList<>();

        public PopulateDbAsync(MomentRoomDatabase db) {
            mDao = db.momentDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //mDao.deleteAll();
            //moments.add(createMoment(R.drawable.joe2));
            //moments.add(createMoment(R.drawable.joe3));
            //for (Moment moment : moments) {
            //    mDao.insert(moment);
            //}
            return null;
        }
    }

    private static Moment createMoment(int path) {
        Moment moment = new Moment("Joe");
        moment.setImageFilePathInt(path);
        return moment;
    }
}
