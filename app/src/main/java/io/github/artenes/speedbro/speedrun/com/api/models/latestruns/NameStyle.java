
package io.github.artenes.speedbro.speedrun.com.api.models.latestruns;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NameStyle {

    @SerializedName("style")
    @Expose
    public String style;
    @SerializedName("color-from")
    @Expose
    public ColorFrom colorFrom;
    @SerializedName("color-to")
    @Expose
    public ColorTo colorTo;

}
