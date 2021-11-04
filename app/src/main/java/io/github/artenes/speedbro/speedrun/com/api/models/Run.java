
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Run {

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
    public Players__1 players;
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
