package com.example.tourist.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MomentDAO {
    @Insert
    void insert(Moment moment);

    @Query("SELECT * FROM moment ORDER BY title ASC")
    LiveData<List<Moment>> getAllMoments();

    @Query("SELECT * FROM moment WHERE journey_id = :journeyId")
    LiveData<List<Moment>> getAllJourneyMoments(int journeyId);

    @Query("SELECT * FROM moment ORDER BY title ASC")
    List<Moment> getNotLiveMoments();

    @Update
    void updateMoment(Moment moment);

    @Query("DELETE FROM moment")
    void deleteAll();
}
