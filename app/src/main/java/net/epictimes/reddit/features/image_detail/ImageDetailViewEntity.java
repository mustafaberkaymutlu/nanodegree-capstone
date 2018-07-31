package net.epictimes.reddit.features.image_detail;

public class ImageDetailViewEntity {

    private final String imageUrl;

    public ImageDetailViewEntity(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
