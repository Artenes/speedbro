
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data__1 {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("weblink")
    @Expose
    public String weblink;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("rules")
    @Expose
    public String rules;
    @SerializedName("players")
    @Expose
    public Players players;
    @SerializedName("miscellaneous")
    @Expose
    public Boolean miscellaneous;
    @SerializedName("links")
    @Expose
    public List<Link__1> links = null;

}
