package uk.ac.shef.oak.com6510.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents the Moment entity in the Room database.
 *
 * One one of the member fields of this entity is image file path. There's two types of image file
 * paths: String and Int. imageFilePath will represent the string type and imageFilePathInt will
 * represent the integer type. Typically, the integer image file path is derived from the
 * "drawable" folder whereas the string one is derived from the "Device File Explorer" storage
 * folder.
 */
@Entity(tableName = "moment")
public class Moment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    // Foreign Key to the Journey entity
    @ColumnInfo(name = "journey_id")
    private long journeyId;

    private String title;
    private String description;
    private String imageFilePath;
    private int imageFilePathInt = -1;

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

    /**
     * The int image file path is set to -1 by default. Therefore, if it has changed, then
     * an int image file path has been set. However, if it remained -1, then the file path
     * of the image is not an int or, in other words, it's not derived from the drawable folder.
     *
     * @return whether the file path is an int or not
     */
    public boolean imageFilePathIsInt() {
        return this.getImageFilePathInt() != -1;
    }

    /**
     * The string image file path is set to null by default. Therefore, if it has changed, then
     * a string image file path has been set. However, if it remained null, then the file path
     * of the image is not a string.
     *
     * @return return whether the path is a string or not
     */
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
