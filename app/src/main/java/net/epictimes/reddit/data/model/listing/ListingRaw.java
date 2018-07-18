package net.epictimes.reddit.data.model.listing;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.child.ChildRaw;

import java.util.List;

public class ListingRaw {

    @SerializedName("before")
    private String before;

    @SerializedName("after")
    private String after;

    @SerializedName("children")
    private List<ChildRaw> children;

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    public List<ChildRaw> getChildren() {
        return children;
    }
}
