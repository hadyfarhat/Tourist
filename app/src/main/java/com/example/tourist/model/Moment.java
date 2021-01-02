package com.example.tourist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moment")
public class Moment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "journey_id")
    private long journeyId;
    private String title;
    private String description;
    private String imageFilePath;
    private int imageFilePathInt = -1;
    // TODO timestamp member field

    public int getId() {
        return this.id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFilePath() {
        return this.imageFilePath;
    }

    public void setImageFilePath(@NonNull String path) {
        this.imageFilePath = path;
    }

    public int getImageFilePathInt() {
        return this.imageFilePathInt;
    }

    public void setImageFilePathInt(@NonNull int imageFilePathInt) {
        this.imageFilePathInt = imageFilePathInt;
    }

    public boolean imageFilePathIsInt() {
        return this.getImageFilePathInt() != -1;
    }

    public boolean imageFilePathIsString() {
        return this.getImageFilePath() != null;
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
    }
}
