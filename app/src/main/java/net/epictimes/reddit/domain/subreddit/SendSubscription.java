package net.epictimes.reddit.domain.subreddit;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditRepository;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Completable;
import polanski.option.Option;

public class SendSubscription implements Interactor.SendInteractor<SendSubscription.Params> {

    @NonNull
    private final SubredditRepository subredditRepository;

    @Inject
    public SendSubscription(@NonNull SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @NonNull
    @Override
    public Completable getCompletable(@Nonnull Option<Params> params) {
        final Params params1 = OptionUtils.someOrThrow(params);
        final String subredditName = params1.subredditName;
        final Action action = params1.action;
        final SubscribeRequest subscribeRequest = new SubscribeRequest(mapToDataAction(action), false, subredditName);
        return subredditRepository.sendSubscription(subscribeRequest);
    }

    private SubscribeRequest.Action mapToDataAction(Action action) {
        switch (action) {
            case SUBSCRIBE:
                return SubscribeRequest.Action.SUBSCRIBE;
            case UNSUBSCRIBE:
                return SubscribeRequest.Action.UNSUBSCRIBE;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static class Params {
        private final String subredditName;
        private final Action action;

        public Params(String subredditName, Action action) {
            this.subredditName = subredditName;
            this.action = action;
        }
    }

    public enum Action {
        SUBSCRIBE, UNSUBSCRIBE
    }

}
