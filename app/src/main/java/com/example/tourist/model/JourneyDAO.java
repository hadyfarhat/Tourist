package com.example.tourist.model;

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

    @Transaction
    @Query("SELECT * FROM journey")
    public List<JourneyWithMoments> getJourneyWithMoments();
}
