package uk.ac.shef.oak.com6510.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Represents the one-to-many relationship between a Journey and Moment.
 * One Journey has many moments. One moment can only belong to one journey.
 */
public class JourneyWithMoments {
    @Embedded public Journey journey;
    @Relation(
            parentColumn = "id",
            entityColumn = "journey_id"
    )
    public List<Moment> moments;
}
