
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data__2 {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("released")
    @Expose
    public Integer released;
    @SerializedName("links")
    @Expose
    public List<Link__5> links = null;

}
