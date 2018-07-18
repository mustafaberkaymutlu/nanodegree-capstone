package net.epictimes.reddit.data.model.listing;

import com.google.gson.annotations.SerializedName;

public class ListingResponse {

    @SerializedName("data")
    private ListingRaw data;

    public ListingRaw getData() {
        return data;
    }
}
