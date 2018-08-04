package net.epictimes.reddit.features.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.ItemClickListener;

import org.apache.commons.lang3.StringUtils;

public class FeedViewHolder extends RecyclerView.ViewHolder {

    public static int LAYOUT_ID = R.layout.list_item_post;

    private final ImageView imageViewIcon;
    private final TextView textViewUserName;
    private final TextView textViewSubredditName;
    private final TextView textViewPostTitle;
    private final TextView textViewPostSelfText;
    private final TextView textViewTimeAgo;
    private final ImageView imageViewPostImage;
    private final ImageView imageViewPlay;

    FeedViewHolder(View itemView,
                   ItemClickListener postClickListener,
                   ItemClickListener imageClickListener) {
        super(itemView);

        imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        textViewUserName = itemView.findViewById(R.id.textViewUserName);
        textViewSubredditName = itemView.findViewById(R.id.textViewSubredditName);
        textViewPostTitle = itemView.findViewById(R.id.textViewPostTitle);
        textViewPostSelfText = itemView.findViewById(R.id.textViewPostSelfText);
        textViewTimeAgo = itemView.findViewById(R.id.textViewTimeAgo);
        imageViewPostImage = itemView.findViewById(R.id.imageViewPostImage);
        imageViewPlay = itemView.findViewById(R.id.imageViewPlay);

        imageViewPostImage.setOnClickListener(v -> imageClickListener.onItemClicked(getAdapterPosition()));
        itemView.setOnClickListener(v -> postClickListener.onItemClicked(getAdapterPosition()));
    }

    public void bind(@NonNull Post post) {
        if (post.getThumbnail() != null) {
            GlideApp
                    .with(imageViewIcon)
                    .load(post.getThumbnail())
                    .into(imageViewIcon);
        }

        if (StringUtils.isBlank(post.getPreviewImage())) {
            imageViewPostImage.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
        } else {
            imageViewPostImage.setVisibility(View.VISIBLE);
            GlideApp
                    .with(imageViewPostImage)
                    .load(post.getPreviewImage())
                    .into(imageViewPostImage);

            if (post.isVideo()) {
                imageViewPlay.setVisibility(View.VISIBLE);
            } else {
                imageViewPlay.setVisibility(View.GONE);
            }
        }

        final String prefixedAuthorName = textViewUserName.getContext().getString(R.string.prefixed_author_name,
                post.getAuthor());
        textViewUserName.setText(prefixedAuthorName);
        textViewSubredditName.setText(post.getSubredditNamePrefixed());
        textViewPostTitle.setText(Html.fromHtml(post.getTitle()));
        textViewPostSelfText.setText(post.getSelfText());
        textViewTimeAgo.setText(TimeAgo.using(post.getCreatedUtc() * 1000));
    }
}
