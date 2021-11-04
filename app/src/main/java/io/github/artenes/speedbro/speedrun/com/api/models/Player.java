
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Player {

    @SerializedName("rel")
    @Expose
    public String rel;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("names")
    @Expose
    public Names__1 names;
    @SerializedName("pronouns")
    @Expose
    public String pronouns;
    @SerializedName("weblink")
    @Expose
    public String weblink;
    @SerializedName("name-style")
    @Expose
    public NameStyle nameStyle;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("signup")
    @Expose
    public String signup;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("twitch")
    @Expose
    public SocialLink twitch;
    @SerializedName("hitbox")
    @Expose
    public Object hitbox;
    @SerializedName("youtube")
    @Expose
    public SocialLink youtube;
    @SerializedName("twitter")
    @Expose
    public SocialLink twitter;
    @SerializedName("speedrunslive")
    @Expose
    public Object speedrunslive;
    @SerializedName("assets")
    @Expose
    public Assets__1 assets;
    @SerializedName("links")
    @Expose
    public List<Link__3> links = null;

}
