package net.epictimes.reddit.data.model.image;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.image.source.SourceRaw;

public class ImageRaw {

    @SerializedName("source")
    private SourceRaw sourceRaw;

    public SourceRaw getSourceRaw() {
        return sourceRaw;
    }
}
