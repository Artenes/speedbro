
package io.github.artenes.speedbro.speedrun.com.api.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Times {

    @SerializedName("primary")
    @Expose
    public String primary;

    @SerializedName("primary_t")
    @Expose
    public String primary_t;

    @SerializedName("realtime_t")
    @Expose
    public String realtime_t;

    @SerializedName("ingame_t")
    @Expose
    public String ingame_t;
}
