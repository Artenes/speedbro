
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RunsData {

    @SerializedName("data")
    @Expose
    public List<Run> data = null;
    @SerializedName("pagination")
    @Expose
    public Pagination pagination;

}
