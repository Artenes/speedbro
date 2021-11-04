
package io.github.artenes.speedbro.speedrun.com.api.models.latestruns;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("names")
    @Expose
    public Names names;
    @SerializedName("abbreviation")
    @Expose
    public String abbreviation;
    @SerializedName("weblink")
    @Expose
    public String weblink;
    @SerializedName("released")
    @Expose
    public Integer released;
    @SerializedName("release-date")
    @Expose
    public String releaseDate;
    @SerializedName("ruleset")
    @Expose
    public Ruleset ruleset;
    @SerializedName("romhack")
    @Expose
    public Boolean romhack;
    @SerializedName("gametypes")
    @Expose
    public List<Object> gametypes = null;
    @SerializedName("platforms")
    @Expose
    public List<String> platforms = null;
    @SerializedName("regions")
    @Expose
    public List<Object> regions = null;
    @SerializedName("genres")
    @Expose
    public List<String> genres = null;
    @SerializedName("engines")
    @Expose
    public List<String> engines = null;
    @SerializedName("developers")
    @Expose
    public List<String> developers = null;
    @SerializedName("publishers")
    @Expose
    public List<String> publishers = null;
    @SerializedName("moderators")
    @Expose
    public Moderators moderators;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("assets")
    @Expose
    public Assets assets;
    @SerializedName("links")
    @Expose
    public List<Link> links = null;

}
