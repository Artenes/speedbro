
package io.github.artenes.speedbro.speedrun.com.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GameSearchResultData {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("names")
    @Expose
    public Names names;

}
