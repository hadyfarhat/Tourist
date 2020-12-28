package com.example.tourist.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MomentDAO {
    @Insert
    void insert(Moment moment);

    @Query("SELECT * FROM moment ORDER BY title ASC")
    LiveData<List<Moment>> getAllMoments();

    @Query("DELETE FROM moment")
    void deleteAll();
}
