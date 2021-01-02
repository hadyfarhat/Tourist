package com.example.tourist.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journey")
public class Journey {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;
    private String startDate;
    private String endDate;
}
