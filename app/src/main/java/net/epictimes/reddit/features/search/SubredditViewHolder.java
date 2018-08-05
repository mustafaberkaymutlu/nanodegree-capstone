package net.epictimes.reddit.features.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.ItemClickListener;

import org.apache.commons.lang3.StringUtils;

public class SubredditViewHolder extends RecyclerView.ViewHolder {
    public static final int LAYOUT_ID = R.layout.list_item_subreddit;

    private final ImageView imageViewIcon;
    private final TextView textViewSubredditName;
    private final TextView textViewSubscriberCount;

    SubredditViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        textViewSubredditName = itemView.findViewById(R.id.textViewSubredditName);
        textViewSubscriberCount = itemView.findViewById(R.id.textViewSubscriberCount);

        itemView.setOnClickListener(v -> itemClickListener.onItemClicked(getAdapterPosition()));
    }

    public void bind(@NonNull SubredditSearch subreddit) {
        final Context context = imageViewIcon.getContext();

        if (StringUtils.isBlank(subreddit.getIconUrl())) {
            final int accentColor = ContextCompat.getColor(context, R.color.accent);
            imageViewIcon.setBackgroundColor(accentColor);
        } else {
            GlideApp
                    .with(imageViewIcon)
                    .load(subreddit.getIconUrl())
                    .into(imageViewIcon);
        }

        textViewSubredditName.setText(context.getString(R.string.prefixed_subreddit_name, subreddit.getName()));
        final String subscriberCount = context.getResources().getQuantityString(R.plurals.subscriber_count,
                subreddit.getSubscriberCount(), subreddit.getSubscriberCount());
        textViewSubscriberCount.setText(subscriberCount);

    }
}
