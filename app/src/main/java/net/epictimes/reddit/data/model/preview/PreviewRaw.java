package net.epictimes.reddit.data.model.preview;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.image.ImageRaw;

import java.util.List;

public class PreviewRaw {

    @SerializedName("images")
    private List<ImageRaw> images;

    public List<ImageRaw> getImages() {
        return images;
    }
}
