package net.epictimes.reddit.features.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.feed.FeedActivity;
import net.epictimes.reddit.features.post_detail.PostDetailActivity;

import dagger.android.AndroidInjection;

public class RedditWidgetProvider extends AppWidgetProvider {
    public static final String POST_DETAIL_ACTION = "net.epictimes.reddit.features.widget.RedditWidgetProvider.POST_DETAIL_ACTION";
    public static final String EXTRA_POST_ID = "net.epictimes.reddit.features.widget.RedditWidgetProvider.POST_ID";

    private static Intent newIntent(@NonNull Context context) {
        return new Intent(context, RedditWidgetProvider.class);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);

        if (POST_DETAIL_ACTION.equals(intent.getAction())) {
            final String postId = intent.getStringExtra(EXTRA_POST_ID);
            context.startActivity(PostDetailActivity.newIntent(context, postId));
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    void updateAppWidget(Context context,
                         AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        final Intent feedIntent = FeedActivity.newIntent(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, feedIntent, 0);

        views.setOnClickPendingIntent(R.id.textViewTitle, pendingIntent);

        final Intent remoteViewsServiceIntent = RedditRemoteViewsService.newIntent(context, appWidgetId);
        views.setRemoteAdapter(R.id.listViewPosts, remoteViewsServiceIntent);

        final Intent postDetailIntent = newIntent(context);
        postDetailIntent.setAction(POST_DETAIL_ACTION);
        postDetailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        final PendingIntent postDetailPendingIntent = PendingIntent.getBroadcast(context, 0, postDetailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.listViewPosts, postDetailPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Helper method for testing widget updates
     */
    public static void updateAllBakingWidgets(@NonNull Context context) {
        final Intent intent = new Intent(context, RedditWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        final int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, RedditWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}
