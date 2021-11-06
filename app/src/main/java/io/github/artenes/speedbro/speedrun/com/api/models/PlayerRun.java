
package io.github.artenes.speedbro.speedrun.com.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PlayerRun {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("weblink")
    @Expose
    public String weblink;
    @SerializedName("game")
    @Expose
    public Game game;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("videos")
    @Expose
    public Videos videos;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("status")
    @Expose
    public Status status;
    @SerializedName("players")
    @Expose
    public List<Player> players;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("submitted")
    @Expose
    public String submitted;
    @SerializedName("times")
    @Expose
    public Times times;
    @SerializedName("system")
    @Expose
    public System system;
    @SerializedName("splits")
    @Expose
    public Object splits;
    @SerializedName("values")
    @Expose
    public Values values;
    @SerializedName("links")
    @Expose
    public List<Link__4> links = null;

}
