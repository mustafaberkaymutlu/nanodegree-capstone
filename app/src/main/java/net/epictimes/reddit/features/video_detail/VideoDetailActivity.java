package net.epictimes.reddit.features.video_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.epictimes.reddit.R;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

import dagger.android.AndroidInjection;

public class VideoDetailActivity extends AppCompatActivity {
    private static final String KEY_URL = "video_url";
    private static final String KEY_VIDEO_POSITION = "video_position";
    private static final String KEY_IS_VIDEO_PLAYING = "is_video_paying";

    private static final int INITIAL_VIDEO_POSITION = 0;
    private static final boolean INITIAL_IS_VIDEO_PLAYING = true;

    private PlayerView playerView;

    private long videoPosition;
    private boolean isVideoPlaying;

    @Nullable
    private SimpleExoPlayer simpleExoPlayer;

    private String videoUrl;

    public static Intent newIntent(@Nonnull Context context, @Nonnull String videoUrl) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(videoUrl);
        final Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(KEY_URL, videoUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        playerView = findViewById(R.id.playerView);

        final Bundle extras = Preconditions.checkNotNull(getIntent().getExtras());
        videoUrl = extras.getString(VideoDetailActivity.KEY_URL);

        if (savedInstanceState == null) {
            videoPosition = INITIAL_VIDEO_POSITION;
            isVideoPlaying = INITIAL_IS_VIDEO_PLAYING;
        } else {
            videoPosition = savedInstanceState.getLong(KEY_VIDEO_POSITION, 0);
            isVideoPlaying = savedInstanceState.getBoolean(KEY_IS_VIDEO_PLAYING, INITIAL_IS_VIDEO_PLAYING);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (simpleExoPlayer != null) {
            outState.putLong(KEY_VIDEO_POSITION, simpleExoPlayer.getCurrentPosition());
            outState.putBoolean(KEY_IS_VIDEO_PLAYING, simpleExoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            configurePlayerView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            configurePlayerView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            releaseVideoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releaseVideoPlayer();
        }
    }

    private void configurePlayerView() {
        initializeVideoPlayer(videoUrl);
    }

    private void initializeVideoPlayer(@NonNull String videoUrl) {
        final DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setControllerAutoShow(false);
        playerView.setPlayer(simpleExoPlayer);

        final Uri videoUri = Uri.parse(videoUrl);
        final String userAgent = Util.getUserAgent(this, "EpicTimesForReddit");

        final DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
        final HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(isVideoPlaying);
        simpleExoPlayer.seekTo(videoPosition);
    }

    private void releaseVideoPlayer() {
        if (simpleExoPlayer != null) {
            videoPosition = simpleExoPlayer.getCurrentPosition();
            isVideoPlaying = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
