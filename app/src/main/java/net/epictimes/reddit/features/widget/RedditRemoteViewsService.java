package net.epictimes.reddit.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.domain.posts.RetrieveBestPosts;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import polanski.option.Option;

public class RedditRemoteViewsService extends RemoteViewsService {

    @Inject
    RetrieveBestPosts retrieveBestPosts;

    public static Intent newIntent(@NonNull Context context, int appWidgetId) {
        final Intent intent = new Intent(context, RedditRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return intent;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyRemoteViewsFactory();
    }

    private class MyRemoteViewsFactory implements RemoteViewsFactory {
        private final List<Post> postList = new ArrayList<>();

        @Override
        public void onCreate() {
            // no-op
        }

        @Override
        public void onDataSetChanged() {
            final List<Post> posts = retrieveBestPosts.getBehaviorStream(Option.none())
                    .map(Listing::getChildren)
                    .blockingFirst();

            addItems(posts);
        }

        @Override
        public void onDestroy() {
            // no-op
        }

        @Override
        public int getCount() {
            return postList.size();
        }

        @Override
        public RemoteViews getViewAt(final int position) {
            final Post post = postList.get(position);

            final RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_widget_post);

            views.setTextViewText(R.id.textViewPostTitle, getReadablePost(post));

            final Bundle extras = new Bundle();
            extras.putString(RedditWidgetProvider.EXTRA_POST_ID, post.getId());
            final Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.textViewPostTitle, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private void addItems(List<Post> posts) {
            postList.clear();
            postList.addAll(posts);
        }

        private CharSequence getReadablePost(Post post) {
            final String prefixedAuthor = getString(R.string.prefixed_author_name, post.getAuthor());
            return prefixedAuthor + ": " + post.getTitle();
        }

    }
}
