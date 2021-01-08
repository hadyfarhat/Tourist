package com.example.tourist.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/**
 * Represents the Data Access Object of the Journey entity.
 * This class will be mainly used by the Journey Repository
 */
@Dao
 public interface JourneyDAO {
    /**
     * Inserts a journey into the database
     *
     * @param journey journey to be inserted
     */
    @Insert
    void insert(Journey journey);

    /**
     * Deletes all journeys from the database
     */
    @Query("DELETE FROM journey")
    void deleteAll();

    /**
     * Selects the journey with the highest id number
     *
     * @return the journey with the highest id number
     */
    @Query("SELECT * FROM journey ORDER BY id DESC LIMIT 1")
    Journey getLastJourney();

    /**
     * Selects all JourneyWithMoments without setting the data as LiveData.
     * This is usually used to populate the database where live data isn't important.
     *
     * @return a non-live list of {@link JourneyWithMoments}
     */
    @Transaction
    @Query("SELECT * FROM journey")
    List<JourneyWithMoments> getNotLiveJourneyWithMoments();

    /**
     * Selects all JourneyWithMoments and sets the data as LiveData.
     * This will be used by the Repository where live data is important.
     *
     * @return live data list of {@link JourneyWithMoments}
     */
    @Transaction
    @Query("SELECT * FROM journey")
    public LiveData<List<JourneyWithMoments>> getJourneyWithMoments();
}
