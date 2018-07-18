package net.epictimes.reddit.features.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.epictimes.reddit.data.model.post.Post;

import java.util.ArrayList;
import java.util.List;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private final List<Post> postList = new ArrayList<>();

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItems(List<Post> newPosts) {
        postList.clear();
        postList.addAll(newPosts);
        notifyDataSetChanged();
    }
}