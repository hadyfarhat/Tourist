package com.example.tourist.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
 public interface JourneyDAO {
    @Insert
    void insert(Journey journey);

    @Insert
    void insert(Moment moment);

    @Query("DELETE FROM journey")
    void deleteAll();

    @Query("SELECT * FROM journey ORDER BY id DESC LIMIT 1")
    Journey getLastJourney();

    @Transaction
    @Query("SELECT * FROM journey")
    public LiveData<List<JourneyWithMoments>> getJourneyWithMoments();
}
