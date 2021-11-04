
package io.github.artenes.speedbro.speedrun.com.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PlayerRunsData {

    @SerializedName("data")
    @Expose
    public List<PlayerRun> data = null;
    @SerializedName("pagination")
    @Expose
    public Pagination pagination;

}
