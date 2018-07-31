package net.epictimes.reddit.data.model.image.source;

import com.google.gson.annotations.SerializedName;

public class SourceRaw {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
}
