package romanowicz.projectx.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import romanowicz.projectx.APICalls.ItemInfo;

@Entity(tableName = "items_table")
public class SavedItemEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @NonNull
    private String title;
    @NonNull
    private String type;
    private String description;
    private String wikipediaURL;
    private String youtubeURL;


    public void convertFromAPIToEntity(ItemInfo itemInfo) {
        title = itemInfo.getName();
        type = itemInfo.getType();

        description = itemInfo.getDescription();
        wikipediaURL = itemInfo.getWikipediaURL();
        youtubeURL = itemInfo.getYoutubeURL();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setWikipediaURL(String wikipediaURL) {
        this.wikipediaURL = wikipediaURL;
    }

    public String getWikipediaURL() {
        return wikipediaURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }
}
