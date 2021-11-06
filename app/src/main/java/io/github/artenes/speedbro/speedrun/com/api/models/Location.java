
package io.github.artenes.speedbro.speedrun.com.api.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Location {

    @SerializedName("country")
    @Expose
    public Country country;
    @SerializedName("region")
    @Expose
    public Region region;

}
