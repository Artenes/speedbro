
package io.github.artenes.speedbro.speedrun.com.api.models.latestruns;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Pagination {

    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("max")
    @Expose
    public Integer max;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("links")
    @Expose
    public List<Link__6> links = null;

}
