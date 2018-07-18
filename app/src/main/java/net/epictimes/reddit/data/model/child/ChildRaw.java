package net.epictimes.reddit.data.model.child;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.post.PostRaw;

public class ChildRaw {

    @SerializedName("data")
    private PostRaw data;

    public PostRaw getData() {
        return data;
    }
}
