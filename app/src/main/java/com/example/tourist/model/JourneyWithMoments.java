package com.example.tourist.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class JourneyWithMoments {
    @Embedded public Journey journey;
    @Relation(
            parentColumn = "id",
            entityColumn = "journey_id"
    )
    public List<Moment> moments;
}
