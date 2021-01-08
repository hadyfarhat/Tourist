package com.example.tourist.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Represents the Data Access Object of the Moment entity.
 * This class will be mainly used by the Moment Repository
 */
@Dao
public interface MomentDAO {
    /**
     * Inserts a moment into the database
     *
     * @param moment moment to be inserted
     */
    @Insert
    void insert(Moment moment);

    /**
     * Selects all the moments from the database and sets it as live data
     *
     * @return live data list of all moments
     */
    @Query("SELECT * FROM moment ORDER BY title ASC")
    LiveData<List<Moment>> getAllMoments();

    /**
     * Selects all the moments that belong to the passed journey id parameter
     *
     * @param journeyId id of the journey that will be used when searching for the moments
     * @return live data list of all the moments that belong to the passed journey id parameter
     */
    @Query("SELECT * FROM moment WHERE journey_id = :journeyId")
    LiveData<List<Moment>> getAllJourneyMoments(int journeyId);

    /**
     * Selects all the moments from the database without setting them as live data.
     * This is usually used to populate the database where live data isn't important.
     * @return a non-live list of moments
     */
    @Query("SELECT * FROM moment ORDER BY title ASC")
    List<Moment> getNotLiveMoments();

    /**
     * Updates a particular moment in the database
     *
     * @param moment moment to be updated
     */
    @Update
    void updateMoment(Moment moment);

    /**
     * Deletes all moments from the database. This will result in an empty moment table
     */
    @Query("DELETE FROM moment")
    void deleteAll();
}
