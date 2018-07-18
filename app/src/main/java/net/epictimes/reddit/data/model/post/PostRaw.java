package net.epictimes.reddit.data.model.post;

import com.google.gson.annotations.SerializedName;

public class PostRaw {

    @SerializedName("id")
    private String id;

    @SerializedName("description")
    private String description;

    @SerializedName("display_name_prefixed")
    private String displayNamePrefixed;

    @SerializedName("header_img")
    private String headerImg;

    @SerializedName("icon_img")
    private String iconImg;

    @SerializedName("banner_img")
    private String bannerImg;

    @SerializedName("created_utc")
    private Long createdUtc;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getIconImg() {
        return iconImg;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }
}
