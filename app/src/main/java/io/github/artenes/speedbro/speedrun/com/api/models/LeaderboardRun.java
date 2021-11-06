
package io.github.artenes.speedbro.speedrun.com.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class LeaderboardRun {

    @SerializedName("place")
    @Expose
    public Integer place;

    @SerializedName("run")
    @Expose
    public SimpleRun run;

}
