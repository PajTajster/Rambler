package romanowicz.projectx.APICalls;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import romanowicz.projectx.R;

public class ItemInfo implements Serializable {
    @SerializedName("Name")
    private String name;
    @SerializedName("Type")
    private String type;
    @SerializedName("wTeaser")
    private String description;
    @SerializedName("wUrl")
    private String wikipediaURL;
    @SerializedName("yUrl")
    private String youtubeURL;


    ItemInfo(String n, String t, String d, String w, String y) {
        name = n;
        type = t;
        description = d;
        wikipediaURL = w;
        youtubeURL = y;
    }

    public void setNormalTypeField(Context context){
        switch (type.toLowerCase()) {
            case "music":
                type = context.getString(R.string.type_music);
            case "band":
                type = context.getString(R.string.type_band);
                break;
            case "movie":
                type = context.getString(R.string.type_movie);
                break;
            case "show":
                type = context.getString(R.string.type_show);
                break;
            case "podcast":
                type = context.getString(R.string.type_podcast);
                break;
            case "book":
                type = context.getString(R.string.type_book);
                break;
            case "author":
                type = context.getString(R.string.type_author);
                break;
            case "game":
                type = context.getString(R.string.type_game);
                break;
            default:

                break;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWikipediaURL() {
        return wikipediaURL;
    }

    public void setWikipediaURL(String wikipediaURL) {
        this.wikipediaURL = wikipediaURL;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }
}
