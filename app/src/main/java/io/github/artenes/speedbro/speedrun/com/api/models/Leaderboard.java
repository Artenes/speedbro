
package io.github.artenes.speedbro.speedrun.com.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Leaderboard {

    @SerializedName("runs")
    @Expose
    public List<LeaderboardRun> runs;
    @SerializedName("players")
    @Expose
    public PlayerListData players;
    @SerializedName("platforms")
    @Expose
    public PlatformListData platforms;
}
