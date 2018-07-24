package net.epictimes.reddit.features.feed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.epictimes.reddit.data.model.post.Post;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import timber.log.Timber;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private final List<Post> postList = new ArrayList<>();

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(FeedViewHolder.LAYOUT_ID, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setItems(@Nonnull List<Post> newPosts) {
        postList.clear();
        postList.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void addItems(@Nonnull List<Post> newPosts) {
        final int previousSize = postList.size();
        postList.addAll(newPosts);
        notifyItemRangeInserted(previousSize, newPosts.size());
    }

    /**
     * @return Id of the last {@link Post} or null if the list is empty.
     */
    @Nullable
    public String getLastPostId() {
        try {
            final Post lastPost = postList.get(postList.size() - 1);
            return lastPost.getId();
        } catch (IndexOutOfBoundsException e) {
            Timber.e(e);
            return null;
        }
    }
}