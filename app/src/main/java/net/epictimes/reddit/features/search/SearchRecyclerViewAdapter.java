package net.epictimes.reddit.features.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SubredditViewHolder> {

    @Nonnull
    private final List<SubredditSearch> subredditList = new ArrayList<>();

    @Nullable
    private SubredditClickListener subredditClickListener;

    interface SubredditClickListener {
        void onSubredditClicked(@Nonnull SubredditSearch subreddit);
    }

    @NonNull
    @Override
    public SubredditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(SubredditViewHolder.LAYOUT_ID, parent, false);
        final ItemClickListener postItemClickListener = (position) -> {
            if (subredditClickListener != null) {
                subredditClickListener.onSubredditClicked(subredditList.get(position));
            }
        };
        return new SubredditViewHolder(view, postItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubredditViewHolder holder, int position) {
        final SubredditSearch subreddit = subredditList.get(position);
        holder.bind(subreddit);
    }

    @Override
    public int getItemCount() {
        return subredditList.size();
    }

    public void setItems(@Nonnull List<SubredditSearch> newSubreddits) {
        subredditList.clear();
        subredditList.addAll(newSubreddits);
        notifyDataSetChanged();
    }

    public void addItems(@Nonnull List<SubredditSearch> newSubreddits) {
        final int previousSize = subredditList.size();
        subredditList.addAll(newSubreddits);
        notifyItemRangeInserted(previousSize, newSubreddits.size());
    }

    public void setSubredditClickListener(@Nullable SubredditClickListener subredditClickListener) {
        this.subredditClickListener = subredditClickListener;
    }
}
