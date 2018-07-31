package net.epictimes.reddit.data.model.image;

import net.epictimes.reddit.data.model.image.source.Source;

public class Image {

    private final Source source;

    public Image(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }
}
