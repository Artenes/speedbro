
package io.github.artenes.speedbro.speedrun.com.api.models.latestruns;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class System {

    @SerializedName("platform")
    @Expose
    public String platform;
    @SerializedName("emulated")
    @Expose
    public Boolean emulated;
    @SerializedName("region")
    @Expose
    public Object region;

}