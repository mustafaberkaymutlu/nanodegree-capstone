package net.epictimes.reddit.features.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.util.ItemClickListener;

public class SubredditViewHolder extends RecyclerView.ViewHolder {
    public static final int LAYOUT_ID = R.layout.list_item_subreddit;

    SubredditViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);
    }

    public void bind(@NonNull Subreddit subreddit) {

    }
}
