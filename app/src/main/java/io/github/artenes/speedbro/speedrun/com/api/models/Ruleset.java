
package io.github.artenes.speedbro.speedrun.com.api.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Ruleset {

    @SerializedName("show-milliseconds")
    @Expose
    public Boolean showMilliseconds;
    @SerializedName("require-verification")
    @Expose
    public Boolean requireVerification;
    @SerializedName("require-video")
    @Expose
    public Boolean requireVideo;
    @SerializedName("run-times")
    @Expose
    public List<String> runTimes = null;
    @SerializedName("default-time")
    @Expose
    public String defaultTime;
    @SerializedName("emulators-allowed")
    @Expose
    public Boolean emulatorsAllowed;

}
