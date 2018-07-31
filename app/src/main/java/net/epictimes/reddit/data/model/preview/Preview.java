package net.epictimes.reddit.data.model.preview;

import net.epictimes.reddit.data.model.image.Image;

import java.util.List;

public class Preview {

    private final List<Image> images;

    public Preview(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }
}
