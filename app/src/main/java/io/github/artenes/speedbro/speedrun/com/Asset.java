package io.github.artenes.speedbro.speedrun.com;

import com.google.gson.annotations.SerializedName;

public class Asset {

    @SerializedName("cover-large")
    private Image coverLarge;

    public Image getCoverLarge() {
        return coverLarge;
    }
}
